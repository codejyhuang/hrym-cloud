package com.everg.hrym.application.hgx.controller;

import com.everg.hrym.api.flock.facade.FlockFacade;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.common.core.annotation.Allowed;
import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.support.base.BaseConstants;
import com.everg.hrym.common.support.smallProgram.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hong on 2019/3/22.
 */
@RequestMapping("hrym/wechat/flock")
@RestController
public class FlockConsumerController {

    @Autowired
    private FlockFacade flockFacade;


    @Autowired
    private LessonFacade lessonFacade;


    @Autowired
    private TaskFacade taskFacade;
    /**
     * 小程序首页  我 的共修群列表
     *
     * @param param
     * @return
     */
    @RequestMapping("myFlockList")
    @Allowed
    public BaseResult myFlockList(@RequestBody QueryObjectParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.listFlockByUuid(param.getUuid()));
    }


    /**
     * 共修群创建
     *
     * @param param
     * @return
     */
    @RequestMapping("/createFlock")
    @Allowed
    public BaseResult createFlock(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.createFlock(param.getVoList(),param.getUUid(),param.getName(),param.getDedicationVerses(),param.getPrivacy(),param.getIntro()));
    }


    /**
     * 共修群详细信息页面
     *
     * @param param
     * @return
     */
    @RequestMapping("/flockMessage")
    @Allowed
    public BaseResult flockMessage(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.selectById(param.getId(),param.getUUid(),param.getCurrentPage(),param.getPageSize()));
    }




    /**
     * 添加群功课
     *
     * @param param
     * @return
     */
    @RequestMapping("addAssignment")
    @Allowed
    public BaseResult addItem(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockFacade.addItem(param.getVoList(),param.getId(),param.getUUid());
        return new BaseResult(code, message, null);
    }


    /**
     * 删除群功课
     *
     * @param param
     * @return
     */
    @RequestMapping("removeFlockLesson")
    @Allowed
    public BaseResult removeFlockLesson(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockFacade.removeFlockLesson(param.getVoList(),param.getId());
        return new BaseResult(code, message, null);
    }


    /**
     * 邀请页面
     *
     * @param param
     * @return
     */
    @RequestMapping("invitePage")
    @Allowed
    public BaseResult invitePage(@RequestBody WechatFlockUserParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.invitePage(param.getUuid(),param.getId()));
    }



    /**
     * 群统计
     *
     * @param param
     * @return
     */
    @RequestMapping("/flockStatistic")
    @Allowed
    public BaseResult flockStatistic(@RequestBody FlockRecordParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;

        return new BaseResult(code, message, flockFacade.flockStatistic(param.getList(),param.getFlockId(),
                param.getStartTimes(),param.getEndTimes(),
                param.getPageNumber(),param.getPageSize()));
    }



    /**
     * 共修详情 共修群列表
     *
     * @param
     * @return
     */
    @RequestMapping("/itemFlockList")
    @Allowed
    public BaseResult itemFlockList(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.itemFlockList(param.getItemId(),param.getItemContentId(),param.getType(),param.getUserId()));
    }



    /**
     * 单群功课查找
     *
     * @param param
     * @return
     */
    @RequestMapping("/flockItemList")
    @Allowed
    public BaseResult flockItemList(@RequestBody FlockRecordParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.flockItemList(param.getFlockId()));
    }



    /**
     * 查看共修群简介 + 群隐私状态
     *
     * @param param
     * @return
     */
    @RequestMapping("queryFlockIntroAndPrivacy")
    @Allowed
    public BaseResult queryFlock(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.queryFlockIntro(param.getId()));
    }


    /**
     * 编辑 / 更新 群简介  / 群隐私
     *
     * @param param
     * @return
     */
    @RequestMapping("updateFlockIntroOrPrivacy")
    @Allowed
    public BaseResult updateFlock(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockFacade.updateFlock(param.getPrivacy(),param.getIntro(),param.getDedicationVerses(),param.getId());
        return new BaseResult(code, message, null);
    }


    /**
     * 查找共修群里所有功课《去重》
     *
     * @param param
     * @return
     */
    @RequestMapping("queryFlockItemNameList")
    @Allowed
    public BaseResult queryFlockItemNameList(@RequestBody FlockRecordParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.flockItemList(param.getFlockId()));
    }



    /**
     * 统计数据时间
     *
     * @param param
     * @return
     */
    @RequestMapping("flockStatisticTimes")
    @Allowed
    public BaseResult flockStatisticTimes(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.flockStatisticTimes(param.getId()));
    }


    /**
     * 设置功课简介、隐私、量词
     *
     * @param param
     * @return
     */
    @RequestMapping("/flockItemSetting")
    @Allowed
    public BaseResult flockItemSetting(@RequestBody ItemParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        lessonFacade.updateUnitIntro(param.getItemId(),param.getItemContentId(),param.getType(),param.getUserId(),param.getUnit(),param.getIntro());
        return new BaseResult(code, message, null);
    }



    /**
     * 查询功课简介、隐私、量词
     *
     * @param param
     * @return
     */

    @RequestMapping("/queryFlockItemSetting")
    @Allowed
    public BaseResult queryFlockItemSetting(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, lessonFacade.queryLessonPrivacyUnitIntro(param.getItemId(),param.getItemContentId(),param.getType(),param.getUserId()));

    }


    /**
     * 可加入共修群列表
     *
     * @param param
     * @return
     */

    @RequestMapping("/queryJoinFlockList")
    @Allowed
    public BaseResult queryJoinFlockList(@RequestBody QueryObjectParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.queryJoinFlockList(param.getUuid(),param.getCurrentPage(),param.getPageSize(),param.getType(),param.getKeywords()));

    }




    /**
     * 共修群列表排序
     *
     * @param param
     * @return
     */

    @RequestMapping("/updateFlockUserOrder")
    @Allowed
    public BaseResult updateFlockUserOrder(@RequestBody WechatFlockParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockFacade.updateFlockUserOrder(param.getFlockList(),param.getUUid());
        return new BaseResult(code, message, null);

    }



    /**
     * 共修群信息-《每日，周，月，年数据数状图》
     *
     * @param param
     * @return
     */
    @RequestMapping("flockRecordNumDetail")
    @Allowed
    public BaseResult flockNumDetail(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.flockNumDetail(param.getType(),param.getId(),
                param.getCreateTime(),param.getUUid(),param.getTotalFlockNum()));
    }


    /**
     * 共修群详细信息页面 《群本周累计和我的本周累计》
     *
     * @param param
     * @return
     */
    @RequestMapping("flockWeekNumDetail")
    @Allowed
    public BaseResult flockWeekNumDetail(@RequestBody WechatFlockParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.flockWeekNumDetail(param.getUUid(),param.getId()));
    }


    /**
     * 查询任务id
     *
     * @param param
     * @return
     */
    @RequestMapping("queryTaskId")
    @Allowed
    public BaseResult queryTaskId(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, taskFacade.queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(param.getItemId(),param.getItemContentId(),param.getType(),param.getUserId()));
    }





}
