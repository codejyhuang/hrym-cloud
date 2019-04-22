package com.everg.hrym.provider.pray.service;

import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.api.pray.entity.*;
import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.NumUtil;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.pray.mapper.PrayMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrayService {
    
    @Autowired
    private PrayMapper mapper;
    
    @Autowired
    private PrayItemService prayItemService;
    
    @Autowired
    private PrayItemUserCountService prayItemUserCountService;
    
    @Autowired
    private PrayUserService prayUserService;
    
    @Autowired
    private PrayRecordService prayRecordService;



    @Autowired
    private LessonFacade lessonFacade;

    @Autowired
    private TaskFacade taskFacade;
    

    public Map<String, Object> createPray(List<ItemVO> voList, Integer uuid, String prayName, String dedicationVerses, Integer privacy, 
                                          String intro,Integer programType,Integer overMethod,String overTime) {

        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isNotBlank(prayName)) {
            map.put("state", 1);
            map.put("errorMsg", "请输入正确的群名称");
            return map;
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUuid(uuid);
        //创建共修群
        AssistPray assistPray = new AssistPray();
        assistPray.setCreateUser(userAccount);
        assistPray.setCreateTime(DateUtil.currentSecond());
        assistPray.setDayDoneNum(new Double(0));
        assistPray.setTotalDoneNum(new Double(0));
        assistPray.setDedicationVerses(dedicationVerses);
        assistPray.setName(prayName);
        assistPray.setPrivacy(privacy == null ? AssistPray.DEFAULT_PRIVACY_STATE : privacy);
        assistPray.setItemJoinNum(1);
        assistPray.setIntro(intro);
        assistPray.setBeginTime(assistPray.getCreateTime());
        assistPray.setDistinction(programType);
        assistPray.setOverMethod(overMethod);
        assistPray.setOverTime(StringUtils.isNotBlank(overTime) ? DateUtil.getTimeFormat(overTime, DateUtil.DATE_PATTON_DEFAULT) : null);
        assistPray.setItemNum(voList.size());
        mapper.create(assistPray);
        //维护群功课关系
        for (ItemVO itemVO : voList) {
            String unit;
            AssistPrayItem assistPrayItem = new AssistPrayItem();
            assistPrayItem.setCreateId(-1);
            assistPrayItem.setDayDoneNum(new Double(0));
            assistPrayItem.setTotalDoneNum(new Double(0));
            assistPrayItem.setAssistPrayId(assistPray.getId());
            assistPrayItem.setItemId(itemVO.getItemId());
            assistPrayItem.setOrder(0);
            assistPrayItem.setState(0);
            assistPrayItem.setType(itemVO.getType());
            assistPrayItem.setItemContentId(itemVO.getItemContentId() == null ? 0 : itemVO.getItemContentId());
            assistPrayItem.setCreateId(itemVO.getUuid() == null ? 0 : itemVO.getUuid());
            assistPrayItem.setUnit("遍");
            assistPrayItem.setDistinction(programType);
            assistPrayItem.setTargetDoneNum(itemVO.getTargetDoneNum());
            prayItemService.insert(assistPrayItem);


            //维护  功课用户的关系  t_assistPray_item_user_count
            AssistPrayItemUserCount assistPrayItemUserCount = new AssistPrayItemUserCount();
            assistPrayItemUserCount.setItemId(itemVO.getItemId());
            assistPrayItemUserCount.setAssistPrayId(assistPray.getId());
            assistPrayItemUserCount.setItemContentId(itemVO.getItemContentId());
            assistPrayItemUserCount.setUuid(uuid);
            assistPrayItemUserCount.setTotal(new Double(0));
            assistPrayItemUserCount.setType(itemVO.getType());
            assistPrayItemUserCount.setDistinction(programType);
            prayItemUserCountService.save(assistPrayItemUserCount);

        }
        //维护 群 用户表
        AssistPrayUser assistPrayUser = new AssistPrayUser();
        assistPrayUser.setAssistPrayId(assistPray.getId());
        assistPrayUser.setType(AssistPrayUser.ROLE_ADMIN);
        assistPrayUser.setUuid(uuid);
        assistPrayUser.setOrder(0);
        assistPrayUser.setCreateTime(DateUtil.currentSecond());
        assistPrayUser.setDistinction(programType);
        prayUserService.insert(assistPrayUser);
        Integer assistPrayId = assistPray.getId();

        map.put("assistPrayId", assistPrayId);
        map.put("state", 0);

        //更新功课人数
        lessonFacade.updateOnlineNum(voList);
        //维护APP 任务task
        taskFacade.preserveTask(voList, uuid);
        return map;
    }

    public Map<String, Object> assistPrayDetails(Integer prayId, Integer uuid, Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        //查询群信息
        AssistPray assistPray = mapper.queryByPrimaryKey(prayId);
        if (!StringUtils.isNotBlank(assistPray.getIntro())){
            assistPray.setIntro("助念过程中，阿弥陀佛是接人，助念者是帮忙送人，与阿弥陀佛做相同的工作，真正的实现了化度众生的愿望。送人成佛，自己也毕竟成佛，这样也完成了上求佛果的愿望。所以说，助念是菩提心不成空愿的有效行为，请众莲友发心助念。");
        }

        //设置报数显示str
        assistPray.setDayNumStr(NumUtil.getTotalNumStr(assistPray.getDayDoneNum(), "0"));
        assistPray.setTotalNumStr(NumUtil.getTotalNumStr(assistPray.getTotalDoneNum(), "0"));
        UserAccount userAccount = assistPray.getCreateUser();
        //所有群功课
        List<AssistPrayItem> lessonDOList = prayItemService.queryByAssistPrayIdJoinView(assistPray.getId(), uuid);
        PageHelper.startPage(currentPage, pageSize);
        
        List<AssistPrayRecordDO> assistPrayRecordDOList = prayRecordService.queryByAssistPrayId(assistPray.getId(), lessonDOList,uuid);
        //遍历所有功课
        for (AssistPrayRecordDO assistPrayRecordDO : assistPrayRecordDOList) {
            assistPrayRecordDO.setReportStr(NumUtil.getTotalNumStr(assistPrayRecordDO.getNum(), "0") + (StringUtils.isNotBlank(assistPrayRecordDO.getUnit()) ? assistPrayRecordDO.getUnit() : "遍"));
            //遍历所有 群功课
            for (AssistPrayItem lessonDO : lessonDOList) {
                if ((lessonDO.getType().equals(assistPrayRecordDO.getType())
                        && lessonDO.getItemId().equals(assistPrayRecordDO.getItemId())
                        && lessonDO.getItemContentId().equals(assistPrayRecordDO.getItemContentId()))
                ) {
                    assistPrayRecordDO.setLessonName(lessonDO.getLessonName());
                    assistPrayRecordDO.setTimeStr(DateUtil.getTimeChaHour(
                            DateUtil.formatIntDate(assistPrayRecordDO.getCreateTime()), new Date()));
                }
            }
        }
        PageInfo<AssistPrayRecordDO> pageInfo = new PageInfo<>(assistPrayRecordDOList);
        map.put("assistPray", assistPray);
        try {
            if (assistPray.getOverMethod() == 0) {
                String beginTime = DateUtil.timestampToDates(assistPray.getBeginTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
                String overTime = DateUtil.timestampToDates(assistPray.getOverTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
                map.put("beginTimeStr", beginTime + "   " + DateUtil.dateToWeek(beginTime, DateUtil.DATA_PATTON_YYYYMMDD2));
                map.put("overTimeStr", overTime + "   " + DateUtil.dateToWeek(overTime, DateUtil.DATA_PATTON_YYYYMMDD2));//结束时间
                map.put("remainTimeStr", (DateUtil.daysBetween(DateUtil.currentSecond(), assistPray.getOverTime()) + 1) < 0 ? 0 : (DateUtil.daysBetween(DateUtil.currentSecond(), assistPray.getOverTime()) + 1) + "天");//剩余天数
            }
            String createTime = DateUtil.timestampToDates(assistPray.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
            map.put("createTimeStr",createTime + "   " + DateUtil.dateToWeek(createTime, DateUtil.DATA_PATTON_YYYYMMDD2));
            map.put("timeStr", (DateUtil.daysBetween(assistPray.getCreateTime(), DateUtil.currentSecond()) + 1) + "天");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Integer> userIds = prayUserService.queryUuidListByAssistPrayId(prayId);
        map.put("isJoin", userIds.contains(uuid) ? 1 : 0);
        map.put("isAdmin", uuid.equals(userAccount.getUuid()) ? 1 : 0);
        map.put("lessonList", lessonDOList);
        map.put("recordList", assistPrayRecordDOList);
        map.put("totalPage", pageInfo.getPages());
        map.put("recordTotal", pageInfo.getTotal());
        map.put("hasNextPage", pageInfo.isHasNextPage());
        return map;

    }
}
