package com.everg.hrym.application.hgx.controller;

import com.everg.hrym.api.flock.facade.FlockUserFacade;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.common.core.annotation.Allowed;
import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.support.base.BaseConstants;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import com.everg.hrym.common.support.smallProgram.WechatFlockParam;
import com.everg.hrym.common.support.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import com.everg.hrym.common.core.entity.BaseResult;

@RestController
@RequestMapping("hrym/wechat/flockUser")
public class FlockUserConsumerController {


    @Autowired
    private FlockUserFacade flockUserFacade;


    /**
     * 共修群群成员列表
     *
     * @param param
     * @return
     */
    @RequestMapping("flockMember")
    @Allowed
    public BaseResult flockMember(@RequestBody QueryObjectParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockUserFacade.flockUserList(param.getId(),param.getUuid(),param.getCurrentPage(),param.getPageSize(),param.getKeywords()));
    }


    /**
     * 加入共修群
     *
     * @param param
     * @return
     */
    @RequestMapping("joinFlock")
    @Allowed
    public BaseResult joinFlock(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockUserFacade.joinFlock(param.getId(), param.getUUid());
        return new BaseResult();
    }


    /**
     * 删除群成员
     *
     * @param param
     * @return
     */
    @RequestMapping("removeFlockUser")
    @Allowed
    public BaseResult removeFlockUser(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockUserFacade.removeFlockUser(param.getUserIds(),param.getUserIds().get(0), param.getId());
        return new BaseResult(code, message, null);
    }

    /**
     * 退出共修群
     *
     * @param param
     * @return
     */
    @RequestMapping("dropFlock")
    @Allowed
    public BaseResult dropFlock(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockUserFacade.dropFlock(param.getId(), param.getUUid());
        return new BaseResult(code, message, null);
    }


    /**
     * 解散共修群
     *
     * @param param
     * @return
     */
    @RequestMapping("dissolveFlock")
    @Allowed
    public BaseResult dissolveFlock(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockUserFacade.dissolveFlock(param.getId(), param.getUUid());
        return new BaseResult(code, message, null);
    }

}
