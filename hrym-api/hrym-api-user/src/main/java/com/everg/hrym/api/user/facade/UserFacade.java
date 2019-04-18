package com.everg.hrym.api.user.facade;

import com.everg.hrym.api.user.configuration.UserConfiguration;
import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.api.user.fallback.UserFacadeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-user", configuration = UserConfiguration.class,fallbackFactory = UserFacadeFallbackFactory.class)
public interface UserFacade {

    @PostMapping("provider/wxUser/getSessionkey")
    Map<String, Object> getSessionkey(@RequestParam("encryptedData") String encryptedData,
                                      @RequestParam("iv") String iv,
                                      @RequestParam("jscode") String jscode,
                                      @RequestParam("programType") Integer programType);

    @PostMapping("provider/user/queryByUuid")
    UserAccount queryByUuid(@RequestParam("uuid") Integer uuid);

    @PostMapping("provider/wxUser/getPhoneNumber")
    Map<String, Object> getPhoneNumber(@RequestParam("sessionKey") String sessionKey, @RequestParam("encryptedData") String encryptedData, @RequestParam("iv") String iv, @RequestParam("uuid") Integer uuid);

}
