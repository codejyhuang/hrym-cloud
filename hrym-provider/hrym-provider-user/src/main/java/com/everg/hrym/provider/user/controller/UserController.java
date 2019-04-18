package com.everg.hrym.provider.user.controller;


import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.provider.user.service.UserAccountService;
import com.everg.hrym.provider.user.service.WxUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private WxUserAccountService wxUserAccountService;


    @PostMapping("provider/wxUser/getSessionkey")
    Map<String, Object> getSessionkey(@RequestParam("encryptedData") String encryptedData,
                                      @RequestParam("iv") String iv,
                                      @RequestParam("jscode") String jscode,
                                      @RequestParam("programType") Integer programType)  {
        try {
            return wxUserAccountService.getSessionkey(encryptedData, iv, jscode, programType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("provider/user/queryByUuid")
    UserAccount queryByUuid(@RequestParam("uuid") Integer uuid) {
        return userAccountService.queryByUuid(uuid);
    }


    @PostMapping("provider/wxUser/getPhoneNumber")
    Map<String, Object> getPhoneNumber(@RequestParam("sessionKey") String sessionKey, @RequestParam("encryptedData") String encryptedData, @RequestParam("iv") String iv, @RequestParam("uuid") Integer uuid) {
        return wxUserAccountService.getPhoneNumber(sessionKey, encryptedData, iv, uuid);
    }

}
