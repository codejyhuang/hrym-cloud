package com.everg.hrym.provider.lesson.controller;

import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    /**
     * 根系功课  online_num
     *
     * @param list 功课集合
     */
    @PostMapping("updateOnlineNum")
    public void updateOnlineNum(@RequestBody List<ItemVO> list) {
        lessonService.updateOnlineNum(list);
    }

    /**
     * 设置功课 量词 简介
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param unit
     * @param uuid
     * @param intro
     */
    @PostMapping("updateUnitIntro")
    void updateUnitIntro(@RequestParam("itemId")Integer itemId, @RequestParam("itemContentId")Integer itemContentId,
                         @RequestParam("type")Integer type, @RequestParam("uuid") Integer uuid,
                         @RequestParam(value = "unit",required = false)String unit, @RequestParam(value = "intro",required = false)String intro){
        lessonService.updateLessonPrivacyUnitIntro(itemId,itemContentId,type,uuid,unit,intro);
    }

    /**
     * 查询功课隐私量词
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     */
    @PostMapping("queryLessonPrivacyUnitIntro")
    Map<String, Object> queryLessonPrivacyUnitIntro(@RequestParam("itemId")Integer itemId, @RequestParam("itemContentId")Integer itemContentId,
                                                    @RequestParam("type")Integer type, @RequestParam("uuid") Integer uuid){
        return lessonService.queryLessonPrivacyUnitIntro(itemId,itemContentId,type,uuid);
    }

    /**
     * 查询 功课
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @return 功课实体
     */
    @PostMapping("queryLessonByItemIdContentIdType")
    LessonCommon queryLessonByItemIdContentIdType(@RequestParam("itemId") Integer itemId,
                                                  @RequestParam("itemContentId") Integer itemContentId,
                                                  @RequestParam("type") Integer type){
        return lessonService.queryLessonFromView(itemId,itemContentId,type);
    }

    /**
     * 我的功课 列表 (功课入口进入)
     *
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords     * @return 功课实体
     */
    @PostMapping("selfItemList")
    Map<String, Object> selfItemList(@RequestParam("uuid")Integer uuid ,@RequestParam("currentPage")Integer currentPage,
                                     @RequestParam("pageSize")Integer pageSize,
                                     @RequestParam(value = "type",required = false)Integer type,
                                     @RequestParam(value = "keywords",required = false)String keywords){
        return lessonService.selfItemList(uuid,currentPage,pageSize,type,keywords);
    }

    /**
     * 功课列表
     *
     * @param uuid
     */
    @PostMapping("listAll")
    Map<String,Object> listAll(@RequestParam("uuid") Integer uuid, @RequestParam("currentPage") Integer currentPage,
                               @RequestParam(value = "pageSize",required = false) Integer pageSize,@RequestParam("type") Integer type,
                               @RequestParam(value = "keywords",required = false) String keywords){
        return lessonService.listAll(uuid,currentPage,pageSize,type,keywords);
    }




    /**
     * record 集合
     *
     * @param itemId
     * @param privacy
     */
    @PostMapping("updateCustomPrivacy")
    void updateCustomPrivacy(@RequestParam("itemId") Integer itemId ,@RequestParam("privacy")Integer privacy){
        lessonService.updateCustomPrivacy(itemId,privacy);
    }



    @PostMapping("reportItem")
    Integer reportItem(@RequestParam("num") Double num,
                       @RequestParam("type") Integer type,
                       @RequestParam("uuid") Integer uuid,
                       @RequestParam("itemId")Integer itemId,
                       @RequestParam("itemContentId")Integer itemContentId,
                       @RequestParam("recordMethod")Integer recordMethod){
        return lessonService.reportItem(num,type,uuid,itemId,itemContentId,recordMethod);
    }




    /**
     * 创建自建功课
     * @param uuid
     * @param lessonName
     * @param unit
     * @param intro
     * @param privacy
     */
    @PostMapping("buildCustom")
    void buildCustom(@RequestParam("uuid") Integer uuid,@RequestParam("lessonName") String lessonName,
                     @RequestParam(value = "unit",required = false) String unit,
                     @RequestParam(value = "intro",required = false) String intro,
                     @RequestParam(value = "info",required = false) String info,
                     @RequestParam("privacy") Integer privacy){
        lessonService.buildCustom(uuid,lessonName,unit,intro,info,privacy);
    }




}
