package com.everg.hrym.api.flock.facade;

import com.everg.hrym.api.flock.configuration.FlockConfiguration;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.fallback.FlockFacadeFallbackFactory;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import com.everg.hrym.common.support.vo.ItemVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-flock", configuration = FlockConfiguration.class,fallbackFactory = FlockFacadeFallbackFactory.class)
public interface FlockFacade {

    /**
     * 创建共修群
     * @param voList
     * @param uuid
     * @param flockName
     * @param dedicationVerses
     * @param intro
     * @param privacy
     * @param intro
     */
    @RequestMapping(value = "provider/flock/createFlock", method = RequestMethod.POST)
    Map<String, Object> createFlock(@RequestBody List<ItemVO> voList, @RequestParam("uuid")Integer uuid , @RequestParam("flockName")String flockName,
                                    @RequestParam(value = "dedicationVerses",required = false)String dedicationVerses,
                                    @RequestParam("privacy")Integer privacy, @RequestParam(value = "intro",required = false)String intro);

    /**
     * 查询 用户 群集合
     * @param uuid
     * @return
     */
    @RequestMapping(value = "provider/flock/listFlockByUuid", method = RequestMethod.POST)
    Map<String, Object> listFlockByUuid(@RequestParam("uuid") Integer uuid);

    /**
     * 群详情
     * @param flockId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "provider/flock/selectById", method = RequestMethod.POST)
    Map<String, Object> selectById(@RequestParam("flockId")Integer flockId ,@RequestParam("uuid") Integer uuid,
                                   @RequestParam("currentPage")Integer currentPage,@RequestParam("pageSize") Integer pageSize );

    /**
     * 添加功课 群
     * @param voList
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/addItem")
    void addItem(@RequestBody List<ItemVO> voList, @RequestParam("flockId")Integer flockId,@RequestParam("uuid")Integer uuid);


    @PostMapping("/provider/flock/report/flockCountReport")
    Map<String,Object> flockCountReport(@RequestBody List<Integer> flockIds, @RequestParam("uuid")Integer uuid,
                                        @RequestParam("itemId")Integer itemId,
                                        @RequestParam("itemContentId") Integer itemContentId,@RequestParam("type")Integer type,
                                        @RequestParam(value = "lat",required = false)Float lat,@RequestParam(value = "lon",required = false)Float lon,@RequestParam("num")Double num);


    /**
     * 删除群功课
     * @param voList
     * @param flockId
     */
    @PostMapping("provider/flock/removeFlockLesson")
    void removeFlockLesson(@RequestBody List<ItemVO> voList,@RequestParam("flockId")Integer flockId);

    /**
     * 邀请页面
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/invitePage")
    Map<String, Object> invitePage(@RequestParam("uuid") Integer uuid ,@RequestParam("id") Integer flockId);

    /**
     * 统计群功课
     * @param list1
     * @return
     */
    @PostMapping("provider/flock/flockStatistic")
    List flockStatistic(@RequestBody List<FlockRecordVo> list1, @RequestParam("flockId") Integer flockId,
                        @RequestParam("startTimes") String startTimes, @RequestParam("endTimes") String endTimes,
                        @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize);

    /**
     * 群功课 集合
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/flockItemList")
    List<FlockItem> flockItemList(@RequestParam("flockId")Integer flockId);

    /**
     * 群 简介 隐私 回响
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/queryFlockIntro")
    Map<String, Object> queryFlockIntro(@RequestParam("flockId") Integer flockId);

    /**
     * 根系群 简介 隐私 回向
     * @param privacy
     * @param intro
     * @param dedicationVerses
     * @param flockId
     */
    @PostMapping("provider/flock/updateFlock")
    void updateFlock(@RequestParam(value = "privacy",required = false)Integer privacy,@RequestParam(value = "intro",required = false)String intro,
                     @RequestParam(value = "dedicationVerses",required = false)String dedicationVerses,@RequestParam("flockId")Integer flockId);

    /**
     * 统计数据时创建日期和结束时间
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/flockStatisticTimes")
    Map<String, Object> flockStatisticTimes(@RequestParam("id") Integer flockId);

    /**
     * 共修广场
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords     * @return
     */
    @PostMapping("provider/flock/queryJoinFlockList")
    Map<String, Object>  queryJoinFlockList(@RequestParam("uuid")Integer uuid ,
                                            @RequestParam("currentPage")Integer currentPage,
                                            @RequestParam("pageSize")Integer pageSize,
                                            @RequestParam(value = "type",required = false)Integer type,
                                            @RequestParam(value = "keywords",required = false)String keywords);

    /**
     * 共修群 排序
     * @param flockList
     * @param uuid
     * @return
     */
    @PostMapping("provider/flock/updateFlockUserOrder")
    void updateFlockUserOrder(@RequestBody List<FlockRecordVo> flockList,@RequestParam("uuid") Integer uuid );

    /**
     * 共修群信息-《每日，周，月，年数据数状图》
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/flockNumDetail")
    Map<String,Object> flockNumDetail(@RequestParam("type") Integer type,@RequestParam("id") Integer flockId,@RequestParam(value = "createTime",required = false) Integer createTime,
                                      @RequestParam("uuid") Integer uuid,@RequestParam("totalFlockNum") Double totalFlockNum);

    /**
     * 群详细《本周群数据和我的本周上周数据》
     *
     * @param uuid
     * @return
     */
    @PostMapping("provider/flock/flockWeekNumDetail")
    Map<String,Object>  flockWeekNumDetail(@RequestParam("uuid") Integer uuid,@RequestParam("id") Integer flockId);


    /**
     *  根据用户功课 查询群列表
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @return
     */
    @PostMapping("provider/flock/itemFlockList")
    Map<String,Object> itemFlockList(@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                                     @RequestParam("type") Integer type,@RequestParam("uuid")Integer uuid);


    /**
     * 共修详情  共修群功课详情 卡片
     *
     * @param flockId
     * @param uuid
     * @return
     */
    @PostMapping("provider/flock/flockMessageCard")
    Map<String,Object> flockMessageCard(@RequestParam("flockId")Integer flockId,@RequestParam("uuid")Integer uuid,
                                        @RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                                        @RequestParam("type")Integer type);



    /*
        合并账号 更新数据
     */
    @PostMapping("provider/flock/mergeAccount")
    void mergeAccount(@RequestParam("uuid") Integer uuid, @RequestParam("oldUuid") Integer oldUuid);


}

