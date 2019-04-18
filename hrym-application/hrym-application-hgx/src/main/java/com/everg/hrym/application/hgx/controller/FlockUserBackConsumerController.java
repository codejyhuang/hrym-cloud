package com.everg.hrym.application.hgx.controller;

import com.everg.hrym.api.back.facade.BackFacade;
import com.everg.hrym.common.core.annotation.Allowed;
import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.support.base.BaseConstants;
import com.everg.hrym.common.support.smallProgram.FlockUserBackParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by hrym13 on 2019/3/27.
 */
@RestController
@RequestMapping("hrym/wechat/flockBack")
public class FlockUserBackConsumerController {
    
    
    @Autowired
    private BackFacade backFacade;

    /**
     * 上次特别回向文
     *
     * @param param
     * @return
     */
    @RequestMapping("lastSpecialBack")
    @Allowed
    public BaseResult lastSpecialBack(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        return new BaseResult(code, message, backFacade.lastSpecialBack(param.getItemType(), param.getItemId(), param.getUuid(), param.getContentId()));
    }
    
    /**
     * 功课特别回向文录入
     *
     * @param param
     * @return
     */
    @RequestMapping("insertSpecialBack")
    @Allowed
    public BaseResult insertSpecialBack(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        backFacade.insertSpecialBack(param);
        return new BaseResult(code, message, null);
    }
    
    /**
     * 编辑特别回向文
     *
     * @param param
     * @return
     */
    @RequestMapping("updateSpecialBack")
    @Allowed
    public BaseResult updateSpecialBack(@RequestBody FlockUserBackParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        backFacade.updateSpecialBack(param.getId(), param.getInfo());
        return new BaseResult(code, message, null);
    }
    
    /**
     * 特别回向详情
     *
     * @param param
     * @return
     */
    @RequestMapping("querySpecialBackDetails")
    @Allowed
    public BaseResult querySpecialBackDetails(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        Map<String, Object> map = backFacade.querySpecialBackDetails(param.getId());
        return new BaseResult(code, message, map);
    }
    
    /**
     * 报数的功课回向
     *
     * @param param
     * @return
     */
    @RequestMapping("queryCountDirection")
    @Allowed
    public BaseResult queryCountDirection(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        Map<String, Object> map = backFacade.queryCountDirection(param);
        return new BaseResult(code, message, map);
    }
    
    /**
     * 特别回向删除
     *
     * @param param
     * @return
     */
    @RequestMapping("deleteSpecialBack")
    @Allowed
    public BaseResult deleteSpecialBack(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        backFacade.deleteSpecialBack(param);
        return new BaseResult(code, message, null);
    }

//    /**
//     * 群回向详情
//     * @param param
//     * @return
//     */
//    @RequestMapping("queryFlockBackDetails")
//    @Allowed
//    public BaseResult queryFlockBackDetails(@RequestBody FlockUserBackParam param) {
//
//        String code = BaseConstants.GWSCODE0000;
//        String message = BaseConstants.GWSMSG0000;
//
//        Map<String,Object> map =flockUserBackService.queryFlockBackDetails(param);
//        return new BaseResult(code, message, map);
//    }
    
    /**
     * 共修回向列表
     *
     * @param param
     * @return
     */
    @RequestMapping("queryDedicationVersesList")
    @Allowed
    public BaseResult queryDedicationVersesList(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        Map<String, Object> map = backFacade.queryDedicationVersesList(param.getUuid(), param.getType(),
                param.getCurrentPage(), param.getPageSize(), param.getFlockId(),param.getTime());
        return new BaseResult(code, message, map);
    }
    
    /**
     * 共修群回向详情dejson
     *
     * @param param
     * @return
     */
    @RequestMapping("queryDedicationVerses")
    @Allowed
    public BaseResult queryDedicationVersesDetails(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        Map<String, Object> map = backFacade.queryDedicationVerses(param.getId());
        return new BaseResult(code, message, map);
    }
    
    /**
     * 共修回向群今日特别回向记录
     *
     * @param param
     * @return
     */
    @RequestMapping("queryDedicationVersesRecordList")
    @Allowed
    public BaseResult queryDedicationVersesRecordList(@RequestBody FlockUserBackParam param) {
        
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        
        Map<String, Object> map = backFacade.queryDedicationVersesRecordList( param.getTimeStr(),
                param.getCurrentPage(), param.getPageSize(),
                param.getFlockId(), param.getYmd());
        return new BaseResult(code, message, map);
    }

}
