package com.everg.hrym.api.user.fallback;

import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.api.user.facade.UserFacade;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class UserFacadeFallbackFactory implements FallbackFactory<UserFacade> {

    @Override
    public UserFacade create(Throwable throwable) {
        return new UserFacade() {


            @Override
            public Map<String, Object> getSessionkey(String encryptedData, String iv, String jscode, Integer programType) {
                return null;
            }

            @Override
            public UserAccount queryByUuid(Integer uuid) {
                return null;
            }


            @Override
            public Map<String, Object> getPhoneNumber(String sessionKey, String encryptedData, String iv, Integer uuid) {
                return null;
            }

        };
    }
}
