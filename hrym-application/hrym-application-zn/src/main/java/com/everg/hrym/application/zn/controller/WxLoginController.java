package com.everg.hrym.application.zn.controller;

import com.everg.hrym.api.user.facade.UserFacade;
import com.everg.hrym.common.core.annotation.Allowed;
import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.support.base.BaseConstants;
import com.everg.hrym.common.support.smallProgram.WxLoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hrym13 on 2018/4/27.
 */
@Controller
@RequestMapping("/hrym/wechat")
public class WxLoginController {

    @Autowired
    private UserFacade userFacade;


    /**
     * 获取openID和sessionkey
     *
     * @param param
     * @return
     */
    @RequestMapping("/assistPray/getSessionkey")
    @Allowed
    @ResponseBody
    public BaseResult getSessionkey(@RequestBody WxLoginParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, userFacade.getSessionkey(param.getEncryptedData(), param.getIv(), param.getJscode(),param.getProgramType()));
    }


}
