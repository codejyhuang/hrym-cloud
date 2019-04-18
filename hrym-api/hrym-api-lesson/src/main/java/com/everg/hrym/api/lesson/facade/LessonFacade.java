package com.everg.hrym.api.lesson.facade;

import com.everg.hrym.api.lesson.configuration.LessonConfiguration;
import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.api.lesson.entity.SearchHistory;
import com.everg.hrym.api.lesson.fallback.LessonFacadeFallbackFactory;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import com.everg.hrym.common.support.vo.ItemVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-lesson", configuration = LessonConfiguration.class,fallback = LessonFacadeFallbackFactory.class)
public interface LessonFacade {

    /**
     * 功课服务   更新功课online_num
     *
     * @param list 集合
     * @return
     */
    @PostMapping("provider/lesson/updateOnlineNum")
    void updateOnlineNum(@RequestBody List<ItemVO> list);

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
    @PostMapping("provider/lesson/updateUnitIntro")
    void updateUnitIntro(@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                         @RequestParam("type")Integer type,@RequestParam("uuid") Integer uuid,
                         @RequestParam(value = "unit",required = false)String unit,@RequestParam(value = "intro",required = false)String intro);

    /**
     * 查询功课隐私量词
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     */
    @PostMapping("provider/lesson/queryLessonPrivacyUnitIntro")
    Map<String, Object> queryLessonPrivacyUnitIntro(@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                                                    @RequestParam("type")Integer type,@RequestParam("uuid") Integer uuid);

    /**
     * 查询 功课
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @return 功课实体
     */
    @PostMapping("provider/lesson/queryLessonByItemIdContentIdType")
    LessonCommon queryLessonByItemIdContentIdType(@RequestParam("itemId") Integer itemId,
                                                  @RequestParam("itemContentId") Integer itemContentId,
                                                  @RequestParam("type") Integer type);

    /**
     * 我的功课 列表 (功课入口进入)
     *
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords     * @return 功课实体
     */
    @PostMapping("provider/lesson/selfItemList")
    Map<String, Object> selfItemList(@RequestParam("uuid")Integer uuid ,@RequestParam("currentPage")Integer currentPage,
                                     @RequestParam("pageSize")Integer pageSize,
                                     @RequestParam(value = "type",required = false)Integer type,
                                     @RequestParam(value = "keywords",required = false)String keywords);



    /**
     * 功课列表
     *
     * @param uuid
     */
    @PostMapping("provider/lesson/listAll")
    Map<String,Object> listAll(@RequestParam("uuid") Integer uuid, @RequestParam("currentPage") Integer currentPage,
                               @RequestParam(value = "pageSize",required = false) Integer pageSize,@RequestParam("type") Integer type,
                               @RequestParam(value = "keywords",required = false) String keywords);


    /**
     * 导入历史记录
     * @param num 上报数
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @param recordMethed
     * @param taskId  任务id
     */
    @PostMapping("provider/lesson/importHistory")
    void importHistory(@RequestParam("num")Double num,@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                       @RequestParam("type")Integer type,@RequestParam("uuid")Integer uuid ,@RequestParam("taskId")Integer taskId,
                       @RequestParam("recordMethod")Integer recordMethed);

    /**
     * record 集合
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param taskId
             * */
    @PostMapping("provider/record/selfRecordHistory")
    Map<String, Object> selfRecordHistory(@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,@RequestParam("type")Integer type,
                                          @RequestParam("uuid")Integer uuid,@RequestParam("currentPage")Integer currentPage,@RequestParam("pageSize")Integer pageSize,
                                          @RequestParam("taskId")Integer taskId);

    /**
     * record 集合
     *
     * @param itemId
     * @param privacy
     */
    @PostMapping("provider/lesson/updateCustomPrivacy")
    void updateCustomPrivacy(@RequestParam("itemId") Integer itemId ,@RequestParam("privacy")Integer privacy);

    /**
     * record 集合
     *
     * @param taskId
     * @param type
     * @param recordId
     * @param num
     * @param ymd
     * @param uuid
     * @param year
     */
    @PostMapping("provider/record/removeSelfRecordHistory")
    void removeSelfRecordHistory(@RequestParam("taskId") Integer taskId,
                                 @RequestParam("type") Integer type,
                                 @RequestParam("recordId") Integer recordId,
                                 @RequestParam("num") Integer num,
                                 @RequestParam("ymd") String ymd,
                                 @RequestParam("uuid")Integer uuid,
                                 @RequestParam("year")Integer year);


    @PostMapping("provider/lesson/reportItem")
    Integer reportItem(@RequestParam("num") Double num,
                       @RequestParam("type") Integer type,
                       @RequestParam("uuid") Integer uuid,
                       @RequestParam("itemId")Integer itemId,
                       @RequestParam("itemContentId")Integer itemContentId,
                       @RequestParam("recordMethod")Integer recordMethod);


    /**
     * 查询 功课搜索历史记录
     * @param uuid
     * @return
     */
    @PostMapping("provider/searchHistory/listSearchHistory")
    List<SearchHistory> listSearchHistory(@RequestParam("uuid") Integer uuid);

    /**
     * 主键 删除搜索历史记录
     * @param id
     */
    @PostMapping("provider/searchHistory/delSearchHistory")
    void delSearchHistory(@RequestParam("id") Integer id);

    /**
     * 清空搜索历史记录
     * @param uuid
     */
    @PostMapping("provider/searchHistory/emptySearchHistory")
    void emptySearchHistory(@RequestParam("uuid") Integer uuid);

    /**
     * 创建自建功课
     * @param uuid
     * @param lessonName
     * @param unit
     * @param intro
     * @param privacy
     */
    @PostMapping("provider/lesson/buildCustom")
    void buildCustom(@RequestParam("uuid") Integer uuid,@RequestParam("lessonName") String lessonName,
                     @RequestParam(value = "unit",required = false) String unit,
                     @RequestParam(value = "intro",required = false) String intro,
                     @RequestParam(value = "info",required = false) String info,
                     @RequestParam("privacy") Integer privacy);


}
