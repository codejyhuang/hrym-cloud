package com.everg.hrym.common.core.decoder;

import com.alibaba.fastjson.JSONObject;
import com.everg.hrym.common.core.exception.HrymFeignException;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by hong on 2019/4/3.
 */
public class HrymResponseEntityDecoder implements Decoder  {
//    ResponseEntityDecoder
//    Default
    private Decoder decoder;

    public HrymResponseEntityDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Object decode(Response response, Type type) throws IOException, FeignException {

        if(response.body()!=null){


            String body = Util.toString(response.body().asReader());
            if(body.indexOf("HrymException")>-1){

                //处理自定义异常
                JSONObject object = JSONObject.parseObject(body);
                // type,code,msg
                String code = object.getString("code");
                String msg = object.getString("message");

                throw new HrymFeignException(code,msg);

            } else {
                response = response.toBuilder().body(body,Util.UTF_8).build();
            }

        }

        if(this.isParameterizeHttpEntity(type)) {
            type = ((ParameterizedType)type).getActualTypeArguments()[0];
            Object decodedObject = this.decoder.decode(response, type);
            return this.createResponse(decodedObject, response);
        } else {
            return this.isHttpEntity(type)?this.createResponse((Object)null, response):this.decoder.decode(response, type);
        }
    }

    private boolean isParameterizeHttpEntity(Type type) {
        return type instanceof ParameterizedType?this.isHttpEntity(((ParameterizedType)type).getRawType()):false;
    }

    private boolean isHttpEntity(Type type) {
        if(type instanceof Class) {
            Class c = (Class)type;
            return HttpEntity.class.isAssignableFrom(c);
        } else {
            return false;
        }
    }

    private <T> ResponseEntity<T> createResponse(Object instance, Response response) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        Iterator var4 = response.headers().keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            headers.put(key, new LinkedList((Collection)response.headers().get(key)));
        }

        return new ResponseEntity(instance, headers, HttpStatus.valueOf(response.status()));
    }
}
