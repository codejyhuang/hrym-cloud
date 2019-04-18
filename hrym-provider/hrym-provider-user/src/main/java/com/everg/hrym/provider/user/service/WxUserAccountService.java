package com.everg.hrym.provider.user.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.common.core.util.AESUtil;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.NumUtil;
import com.everg.hrym.common.core.util.WXAuthUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxUserAccountService {

    @Autowired
    private UserAccountService userAccountService;


    @Transactional
    @LcnTransaction
    public Map<String,Object> getSessionkey(@RequestParam("encryptedData") String encryptedData, @RequestParam("iv") String iv, @RequestParam("jscode") String jscode, @RequestParam("programType") Integer programType) throws IOException {
        String url = "";
        url = WXAuthUtil.getUrl(programType,jscode);
        JSONObject jsonObject = WXAuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String sessionkey = jsonObject.getString("session_key");
        String unionId = null;
        unionId = jsonObject.getString("unionid");

        Map<String, Object> map = new HashMap<>();
        map.put("openid", openid);
        map.put("sessionkey", sessionkey);
        int i = 0;
        byte[] result = null;

        ////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            result = AESUtil.decrypt(encryptedData, sessionkey, iv, "UTF-8");

            if (null != result && result.length > 0) {
                String userInfo = new String(result, "UTF-8");
                JSONObject json = JSON.parseObject(userInfo);
                unionId = (String) json.get("unionId");
                map.put("userInfo", userInfo);
                map.put("status", "1");
                map.put("msg", "解密成功");
                map.put("nickName", (String) json.get("nickName"));
                map.put("sex", (Integer) json.get("gender") == 1 ? 0 : 1);
                map.put("avatarUrl", (String) json.get("avatarUrl"));
                map.put("unionId", (String) json.get("unionId"));
                map.put("openId", (String) json.get("openId"));
                map.put("province", (String) json.get("province"));
                Map<String, Object> map1 = insertWechatUserUId(map);
                map.put("uuid",map1.get("uuid"));
                map.put("flag",map1.get("flag"));
            } else {
                map.put("status", "0");
                map.put("msg", "解密失败");
                map.put("unionId", "");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public Map<String, Object> insertWechatUserUId(Map<String,Object> map) {
        Integer uuid = 0;

        List<UserAccount> users = userAccountService.findUserByUnionIdOpenId(map.get("unionId").toString(),map.get("openId").toString());
        Integer updatedTime = DateUtil.currentSecond();

        if (users.size() > 1) {
            //需要进行账号合并
            uuid = users.get(0).getUuid();
            int num = 0;
            for (int i = 0; i < users.size(); i++) {
                if (i == 0) {
                    continue;
                }
                Integer uuid2 = users.get(i).getUuid();
                //用uuid更新自建功课，任务表，记录表中uuid2的值
                //删除数据库中uuid2的记录
                userAccountService.mergeAccount(uuid,uuid2);


            }
            userAccountService.updateUserAccountNameByUuid(map, updatedTime, uuid);

        } else if (users.size() == 1) {
            uuid = users.get(0).getUuid();
            userAccountService.updateUserAccountNameByUuid(map, updatedTime, uuid);

        } else {
            UserAccount userAccount = new UserAccount();
            userAccount.setSocialUid(map.get("unionId").toString());
            userAccount.setNickname(map.get("nickName").toString());
            userAccount.setSource(1);
            userAccount.setAvatar(map.get("avatarUrl").toString());
            userAccount.setCreatedTime(DateUtil.currentSecond());
            userAccount.setOpenId(map.get("openId").toString());
            userAccount.setStatus(1);
            userAccount.setProvince(map.get("province").toString());
            userAccount.setSex(Integer.valueOf(map.get("sex").toString()));
            userAccountService.insert(userAccount);
            uuid = userAccount.getUuid();
        }
        //根据UUID查找电话号码'flag 0：表示手机号没有授权；1：手机授权',
        Integer flag = userAccountService.findUuidPhoneByUuid(uuid);
        map.put("flag", flag == null ? 0 : flag);
        map.put("uuid", uuid);
        return map;
    }
    
    public Map<String,Object> getPhoneNumber(@RequestParam("sessionKey") String sessionKey,
                                             @RequestParam("encryptedData") String encryptedData,
                                             @RequestParam("iv") String iv,
                                             @RequestParam("uuid") Integer uuid) {
        
    
        Map<String, Object> map = WXAuthUtil.getPhoneNumber(encryptedData, sessionKey, iv);
    
        String phoneNumber = (String) map.get("phoneNumber");
        if (uuid != null && StringUtils.isNotBlank(phoneNumber)) {
            Integer flag = userAccountService.findUuidPhoneByUuid(uuid);
        
            String phone = NumUtil.getFromBase64(phoneNumber);
            if (flag == null) {
                flag = 1;
                userAccountService.insertUuidPhone(flag,uuid, phone);
            } else {
                userAccountService.updateUuidPhoneByUuid(uuid, phone);
            }
        }
        return map;
    }
    
    
}
