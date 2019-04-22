package com.everg.hrym.application.zn.controller;

import com.everg.hrym.api.pray.facade.PrayFacade;
import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.support.base.BaseConstants;
import com.everg.hrym.common.support.smallProgram.AssistPrayParam;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 祈福   助念
 */
@RestController
@RequestMapping("hrym/wechat/assistPray")
public class PrayConsumerController {

    @Autowired
    private PrayFacade prayFacade;



    /**
     * 助念 / 祈福 创建
     * @param param
     * @return
     */
    @RequestMapping("/createAssistPray")
    public BaseResult createAssistPray(@RequestBody AssistPrayParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, prayFacade.createPray(param.getVoList(),param.getUUid(),param.getName(),param.getDedicationVerses(),param.getPrivacy(),param.getIntro(),param.getProgramType(),param.getOverMethod(),param.getOverTime()));
    }

    /**
     * 助念 / 祈福 详情  进行中
     * @param param
     * @return
     */
    @RequestMapping("/assistPrayDetails")
    public BaseResult assistPrayDetails(@RequestBody AssistPrayParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, prayFacade.assistPrayDetails(param.getId(),param.getUUid(),param.getCurrentPage(),param.getPageSize()));
    }
//
//
//    /**
//     * 助念 / 祈福  查询 简介/隐私/回向
//     * @param param
//     * @return
//     */
//    @RequestMapping("/queryIntroAndPrivacy")
//    public BaseResult queryIntroAndPrivacy(@RequestBody AssistPrayParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//        return new BaseResult(code, message, assistPrayService.queryIntroAndPrivacy(param));
//    }
//
//    /**
//     * 助念 / 祈福  设置 简介/隐私/回向
//     * @param param
//     * @return
//     */
//    @RequestMapping("/updateIntroAndPrivacy")
//    public BaseResult updateIntroAndPrivacy(@RequestBody AssistPrayParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//        assistPrayService.updateIntroAndPrivacy(param);
//        return new BaseResult(code, message, null);
//    }
//
//
//    /**
//     * 助念 / 祈福 广场
//     * @param param
//     * @return
//     */
//    @RequestMapping("/assistPraySquareList")
//    public BaseResult assistPraySquareList(@RequestBody QueryObjectParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//        return new BaseResult(code, message, assistPrayService.assistPraySquareList(param));
//    }
//
//    /**
//     * 助念 / 祈福   我发起的
//     * @param param
//     * @return
//     */
//    @RequestMapping("/sponsorAssistPrayList")
//    public BaseResult sponsorAssistPrayList(@RequestBody QueryObjectParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//        return new BaseResult(code, message, assistPrayService.sponsorAssistPrayList(param));
//    }
//
//    /**
//     * 助念 / 祈福   我参加的
//     * @param param
//     * @return
//     */
//    @RequestMapping("/joinAssistPrayList")
//    public BaseResult joinAssistPrayList(@RequestBody QueryObjectParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//        return new BaseResult(code, message, assistPrayService.joinAssistPrayList(param));
//    }
//
//    /**
//     * 助念 / 祈福   首页我参加的
//     * @param param
//     * @return
//     */
//    @RequestMapping("/myAssistPrayList")
//    public BaseResult myAssistPrayList(@RequestBody QueryObjectParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//        Map<String,Object> map = new HashMap<>();
//        map.put("sponsorAssistPrayList",assistPrayService.sponsorAssistPrayList(param));
//        map.put("joinAssistPrayList",assistPrayService.joinAssistPrayList(param));
//        return new BaseResult(code, message, map);
//    }
//
//
//
//    /**
//     * 邀请页面
//     * @param param
//     * @return
//     */
//    @RequestMapping("invitePage")
//    public BaseResult invitePage(@RequestBody AssistPrayParam param) {
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//
//        return new BaseResult(code, message,  assistPrayService.invitePage(param));
//    }



}
