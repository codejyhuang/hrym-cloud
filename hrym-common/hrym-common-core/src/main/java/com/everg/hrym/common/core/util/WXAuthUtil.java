package com.everg.hrym.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信授权登录
 * Created by mj on 2018/4/25.
 */
public class WXAuthUtil {
    
    public static final String APPID = "wx01a274f765225716";
    public static final String APPSECRET = "7601a471da5a9b5e889c1a9fcf592698";
    
    public static final String APPID_WECHAT = "wxb2befeb1da6c0bde";
    public static final String APPSECRET_WECHAT = "eeedfa1f457ac91cc93229a160ca420e";
    
    public static final String APPID_WECHAT_ZN = "wxa8a4546fd04253fe";
    public static final String APPSECRET_WECHAT_ZN = "82f22b20e46c7476fd6f9614e1cfef09";
    
    public static final String APPID_WECHAT_TG = "wx996d2aeff42ac50a";
    public static final String APPSECRET_WECHAT_TG = "eaa88425ff5bcac727b850d96f6b3095";
    
    public static void wxLogin(HttpServletRequest req, HttpServletResponse resp, String backUrl) throws ServletException, IOException {
        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID +
                "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8") +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        resp.sendRedirect(url);
        
    }
    
    /**
     * 公众号微信登录授权回调函数
     *
     * @param code
     * @throws ServletException
     * @throws IOException
     */
    public static String callBack(String code) throws ServletException, IOException {
        
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID +
                "&secret=" + APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        
        JSONObject jsonObject = doGetJson(url);
        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");
        String refresh_token = jsonObject.getString("refresh_token");
        
        //验证access_token是否失效；
        String chickUrl = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid;
        
        JSONObject chickuserInfo = WXAuthUtil.doGetJson(chickUrl);
        System.out.println(chickuserInfo.toString());
        if (!"0".equals(chickuserInfo.getString("errcode"))) {
            // 刷新access_token（如果需要）-----暂时没有使用,参考文档https://mp.weixin.qq.com/wiki，
            String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + openid +
                    "&grant_type=refresh_token&refresh_token=" + refresh_token;
            
            JSONObject refreshInfo = WXAuthUtil.doGetJson(chickUrl);
            
            access_token = refreshInfo.getString("access_token");
        }
        //拉取用户信息(需scope为 snsapi_userinfo)
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token +
                "&openid=" + openid +
                "&lang=zh_CN";
        JSONObject userInfo = doGetJson(infoUrl);
        
        return userInfo.toString();
    }
    
    

    

    
    /**
     * HttpClient服务器模拟浏览器发送请求
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
        JSONObject jsonObject = null;
        
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpGet httpGet = new HttpGet(url);
        
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(8000).setConnectTimeout(8000).build();//设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
        
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //把返回的结果转换为JSON对象
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSON.parseObject(result);
        }
        
        return jsonObject;
    }
    
    
    /**
     * 生成二维码
     */
    public static String buildQrCode(String scene) {
        String appId = APPID_WECHAT;
        String secret = APPSECRET_WECHAT;
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appId +
                    "&secret=" + secret +
                    "&grant_type=client_credential";

//        String parh ="/Users/hrym13/IdeaProjects/hrym/"+DateUtil.currentSecond()+".png";
            JSONObject jsonObject = doGetJson(url);
            String accessToken = jsonObject.getString("access_token");
            URL url1 = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
//            JSONObject jsonObject1 = doGetJson(url1);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "tg-"+scene);
            paramJson.put("page", "pages/login/login");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            /**
             * line_color生效
             * paramJson.put("auto_color", false);
             * JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0);
             * lineColor.put("g", 0);
             * lineColor.put("b", 0);
             * paramJson.put("line_color", lineColor);
             * */
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据/Users/hrym13/IdeaProjects/girl/pom.xml
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            UUID uuid = UUID.randomUUID();
            OutputStream os = new FileOutputStream(new File(uuid + ".png"));
            
            File file = new File(uuid + ".png");
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            String path = uploadFile(uuid + ".png");
            
            return path;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static String uploadFile(String fileName) {
        try {
    
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL("https://www.everglamming.com/hrym-app-web/hrym/api/uploadFiles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            
            // 上传文件
            File file = new File(fileName);
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + fileName
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);
            
            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());
            
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(
                    file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();
            
            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

//             定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            String ret = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                ret = line;
            }
            
            boolean delete = file.delete();
            
            int beginIndex = ret.lastIndexOf("https");
            int endIndex = ret.lastIndexOf("\"");
            return ret.substring(beginIndex, endIndex);
            
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 微信小程序获取手机号
     *
     * @param encryptedData
     * @param sessionkey
     * @param iv
     * @return
     */
    public static Map<String, Object> getPhoneNumber(String encryptedData, String sessionkey, String iv) {
        
        Map<String,Object> map = new HashMap<>();
        // 被加密的数据
//            byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
//            byte[] ivByte = Base64.decode(iv);
        byte[] result = null;
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
                String keyByteStr= new String(keyByte);
                sessionkey=keyByteStr;
            }
            result = AESUtil.decrypt(encryptedData, sessionkey, iv, "UTF-8");
            if (null != result && result.length > 0) {
                String result1 = new String(result, "UTF-8");
                JSONObject json= JSONObject.parseObject(result1);
                map.put("status", "1");
                map.put("msg", "获取成功");
                String phoneNumber =(String) json.get("phoneNumber");
                map.put("phoneNumber",NumUtil.getBase64(phoneNumber) );
                map.put("countryCode",(String) json.get("countryCode"));
            }else {
                map.put("status", "0");
                map.put("msg", "获取失败");
                map.put("phoneNumber", "");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getUrl(Integer programType ,String jscode) {
        String url = "";
        if ((programType.equals(4))) {
            url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WXAuthUtil.APPID_WECHAT +
                    "&secret=" + WXAuthUtil.APPSECRET_WECHAT +
                    "&js_code=" + jscode +
                    "&grant_type=authorization_code";
        }
        if (programType.equals(2)) {
            url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WXAuthUtil.APPID_WECHAT_ZN +
                    "&secret=" + WXAuthUtil.APPSECRET_WECHAT_ZN +
                    "&js_code=" + jscode +
                    "&grant_type=authorization_code";
        }
        if (programType.equals(3)) {
            url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WXAuthUtil.APPID_WECHAT_TG +
                    "&secret=" + WXAuthUtil.APPSECRET_WECHAT_TG +
                    "&js_code=" + jscode +
                    "&grant_type=authorization_code";
        }

        return url;
    }
    
    
}
