package com.everg.hrym.provider.flock.controller;

import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.flock.service.FlockItemUserCountService;
import com.everg.hrym.provider.flock.service.FlockReportCountService;
import com.everg.hrym.provider.flock.service.FlockService;
import com.everg.hrym.provider.flock.service.FlockUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/flock")
public class FlockController {

    @Autowired
    private FlockService flockService;

    @Autowired
    private FlockReportCountService flockCountReportService;


    /**
     * 创建共修群
     *
     * @param voList
     * @param uuid
     * @param flockName
     * @param dedicationVerses
     * @param intro
     * @param privacy
     * @param intro
     */
    @PostMapping(value = "createFlock")
    Map<String, Object> createFlock(@RequestBody List<ItemVO> voList, @RequestParam("uuid") Integer uuid, @RequestParam("flockName") String flockName,
                                    @RequestParam(value = "dedicationVerses", required = false) String dedicationVerses,
                                    @RequestParam("privacy") Integer privacy, @RequestParam(value = "intro", required = false) String intro) {
        return flockService.createFlock(voList, uuid, flockName, dedicationVerses, privacy, intro);
    }

    /**
     * 查询 用户 群集合
     *
     * @param uuid
     * @return
     */
    @PostMapping(value = "listFlockByUuid")
    Map<String, Object> listFlockByUuid(@RequestParam("uuid") Integer uuid) {
        return flockService.listFlockByUuid(uuid);
    }

    /**
     * 群详情
     *
     * @param flockId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping(value = "selectById")
    Map<String, Object> selectById(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid,
                                   @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
        return flockService.selectById(flockId, uuid, currentPage, pageSize);
    }

    @PostMapping("report/flockCountReport")
    Map<String, Object> flockCountReport(@RequestBody List<Integer> flockIds, @RequestParam("uuid") Integer uuid,
                                          @RequestParam("itemId") Integer itemId,
                                         @RequestParam("itemContentId") Integer itemContentId, @RequestParam("type") Integer type,
                                         @RequestParam(value = "lat", required = false) Float lat, @RequestParam(value = "lon", required = false) Float lon, @RequestParam("num") Double num) {
        return flockService.flockCountReport(flockIds, uuid, itemId, itemContentId, type, lat, lon, num);
    }

    /**
     * 添加功课 群
     *
     * @param voList
     * @param flockId
     * @return
     */
    @PostMapping("addItem")
    void addItem(@RequestBody List<ItemVO> voList, @RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid) {
        flockService.addItem(voList, flockId, uuid);
    }

    /**
     * 删除群功课
     *
     * @param voList
     * @param flockId
     */
    @PostMapping("removeFlockLesson")
    void removeFlockLesson(@RequestBody List<ItemVO> voList, @RequestParam("flockId") Integer flockId) {
        flockService.removeFlockLesson(voList, flockId);
    }

    /**
     * 邀请页面
     *
     * @param flockId
     * @return
     */
    @PostMapping("invitePage")
    Map<String, Object> invitePage(@RequestParam("uuid") Integer uuid, @RequestParam("id") Integer flockId) {
        return flockService.invitePage(uuid, flockId);
    }

    /**
     * 统计群功课
     *
     * @param list1
     * @returnf
     */
    @PostMapping("flockStatistic")
    List flockStatistic(@RequestBody List<FlockRecordVo> list1, @RequestParam("flockId") Integer flockId,
                        @RequestParam("startTimes") String startTimes, @RequestParam("endTimes") String endTimes,
                        @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return flockService.flockStatistic(list1, flockId, startTimes, endTimes, pageNumber, pageSize);
    }

    /**
     * 群功课 集合
     *
     * @param flockId
     * @return
     */
    @PostMapping("flockItemList")
    List<FlockItem> flockItemList(@RequestParam("flockId") Integer flockId) {
        return flockService.flockItemList(flockId);
    }

    /**
     * 群 简介 隐私 回响
     *
     * @param flockId
     * @return
     */
    @PostMapping("provider/flock/queryFlockIntro")
    Map<String, Object> queryFlockIntro(@RequestParam("flockId") Integer flockId) {
        return flockService.queryFlockIntro(flockId);
    }

    /**
     * 根系群 简介 隐私 回向
     *
     * @param privacy
     * @param intro
     * @param dedicationVerses
     * @param flockId
     */
    @PostMapping("updateFlock")
    void updateFlock(@RequestParam(value = "privacy", required = false) Integer privacy, @RequestParam(value = "intro", required = false) String intro,
                     @RequestParam(value = "dedicationVerses", required = false) String dedicationVerses, @RequestParam("flockId") Integer flockId) {
        flockService.updateFlock(privacy, intro, dedicationVerses, flockId);
    }

    /**
     * 统计数据时创建日期和结束时间
     *
     * @param flockId
     * @return
     */
    @PostMapping("flockStatisticTimes")
    Map<String, Object> flockStatisticTimes(@RequestParam("id") Integer flockId) {
        return flockService.flockStatisticTimes(flockId);
    }

    /**
     * 共修广场
     *
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords    * @return
     */
    @PostMapping("queryJoinFlockList")
    Map<String, Object> queryJoinFlockList(@RequestParam("uuid") Integer uuid,
                                           @RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("pageSize") Integer pageSize,
                                           @RequestParam(value = "type", required = false) Integer type,
                                           @RequestParam(value = "keywords", required = false) String keywords) {
        return flockService.queryJoinFlockList(uuid, currentPage, pageSize, type, keywords);
    }

    /**
     * 共修群 排序
     *
     * @param flockList
     * @param uuid
     * @return
     */
    @PostMapping("updateFlockUserOrder")
    void updateFlockUserOrder(@RequestBody List<FlockRecordVo> flockList, @RequestParam("uuid") Integer uuid) {
        flockService.updateFlockUserOrder(flockList, uuid);
    }

    /**
     * 共修群信息-《每日，周，月，年数据数状图》
     *
     * @param flockId
     * @return
     */
    @PostMapping("flockNumDetail")
    Map<String, Object> flockNumDetail(@RequestParam("type") Integer type, @RequestParam("id") Integer flockId, @RequestParam(value = "createTime", required = false) Integer createTime,
                                       @RequestParam("uuid") Integer uuid, @RequestParam("totalFlockNum") Double totalFlockNum) {
        return flockService.flockNumDetail(type, flockId, createTime, uuid, totalFlockNum);
    }

    /**
     * 群详细《本周群数据和我的本周上周数据》
     *
     * @param uuid
     * @return
     */
    @PostMapping("flockWeekNumDetail")
    Map<String, Object> flockWeekNumDetail(@RequestParam("uuid") Integer uuid, @RequestParam("id") Integer flockId) {
        return flockService.flockWeekNumDetail(uuid, flockId);
    }


    /**
     * 根据用户功课 查询群列表
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @return
     */
    @PostMapping("itemFlockList")
    Map<String, Object> itemFlockList(@RequestParam("itemId") Integer itemId, @RequestParam("itemContentId") Integer itemContentId,
                                      @RequestParam("type") Integer type, @RequestParam("uuid") Integer uuid) {
        return flockService.itemFlockList(itemId, itemContentId, type, uuid);
    }


    /**
     * 共修详情  共修群功课详情 卡片
     *
     * @param flockId
     * @param uuid
     * @return
     */
    @PostMapping("flockMessageCard")
    Map<String, Object> flockMessageCard(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid,
                                         @RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                                         @RequestParam("type")Integer type) {
        return flockService.flockMessageCard(flockId, uuid,itemId,itemContentId,type);
    }




    /*
        合并账号 更新数据
     */
    @PostMapping("mergeAccount")
    void mergeAccount(@RequestParam("uuid") Integer uuid, @RequestParam("oldUuid") Integer oldUuid){
        flockService.mergeAccount(uuid,oldUuid);
    }



}
