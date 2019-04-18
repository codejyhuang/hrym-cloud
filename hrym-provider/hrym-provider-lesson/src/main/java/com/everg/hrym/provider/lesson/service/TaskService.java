package com.everg.hrym.provider.lesson.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import com.everg.hrym.api.lesson.entity.TaskCommon;
import com.everg.hrym.api.user.facade.UserFacade;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.LunarCalendar;
import com.everg.hrym.common.core.util.NumUtil;
import com.everg.hrym.common.core.util.TableUtil;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.common.support.vo.TaskVO;
import com.everg.hrym.provider.lesson.mapper.TaskCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskCommonMapper mapper;

    @Autowired
    private RecordService recordService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserFacade userFacade;


    /**
     * 维护任务
     *
     * @param list
     * @param uuid
     */
    @Transactional
    @LcnTransaction
    public void preserveTask(List<ItemVO> list, Integer uuid) {
        if (list.size() != 0) {
            for (ItemVO flockItem : list) {
                addTask(uuid, flockItem.getItemId(), flockItem.getItemContentId(), flockItem.getType());
            }
        }

    }

    /**
     * 删除 任务
     *
     * @param taskId
     * @param type
     */
    @Transactional
    public void removeTask(Integer taskId, Integer type, Integer uuid) {
        if (taskId != null) {
            String tableName = TableUtil.getTaskPlanTableNameByItemType(type);
            mapper.updateStateByPrimaryKey(taskId, 0, type, tableName);
        }
    }

    /**
     * 任务排序
     *
     * @param taskList
     */
    @Transactional
    public void updateTaskOrder(List<TaskVO> taskList, Integer uuid) {
        if (taskList.size() != 0) {
            taskList.forEach(x -> mapper.updateTaskOrder(x.getTaskId(), x.getOrder(), x.getType(), TableUtil.getTaskPlanTableNameByItemType(x.getType())));
        }
    }


    /**
     * 添加任务
     *
     * @param uuid          用户uuid
     * @param itemContentId 内容id
     * @param itemId        功课id
     * @param type          功课类型
     */
    @Transactional
    public void addTask(Integer uuid, Integer itemId, Integer itemContentId, Integer type) {
        String tableName = TableUtil.getTaskPlanTableNameByItemType(type);
        int count = mapper.queryCountByItemIdAndItemContentIdAndTableNameAndUuid(type, itemId, itemContentId, uuid, tableName, null);
        //未删除的为零
        if (count == 0) {
            //全部状态为0  创建一条
            TaskCommon taskCommon = new TaskCommon();
            taskCommon.setItemId(itemId);
            taskCommon.setItemContentId(itemContentId);
            taskCommon.setCreateTime(DateUtil.currentSecond());
            taskCommon.setUuid(uuid);
            mapper.insert(taskCommon, tableName, type);
        } else {
            int existCount = mapper.queryCountByItemIdAndItemContentIdAndTableNameAndUuid(type, itemId, itemContentId, uuid, tableName, 1);
            if (existCount == 0) {
                if (type.equals(2)) {
                    //有未删除   更新
                    mapper.updateBookIsExist(type, itemId, itemContentId, uuid, tableName);
                } else {
                    //有未删除   更新
                    mapper.updateIsExist(type, itemId, itemContentId, uuid, tableName);
                }
            }
        }

    }


    /**
     * 我的  task
     *
     * @param uuid
     */
    public Map<String, Object> myTask(Integer uuid) {
        Map<String, Object> map = new HashMap<>();
        List<TaskCommon> taskCommonList = getMyTask(uuid, null);
        Integer dayReportItem = 0;
        if (taskCommonList.size() != 0) {
            for (TaskCommon taskCommon : taskCommonList) {
                dayReportItem += taskCommon.getTodayCommitNum() == 0 ? 0 : 1;
            }
        }
        String strs = DateUtil.format(new Date(), "yyyy年MM月dd日") + "  " + DateUtil.dateToWeek() + "  ";
        LunarCalendar cal = new LunarCalendar();
        map.put("lessonList", taskCommonList);
        map.put("itemNum", taskCommonList.size());
        map.put("dayDoneNum", dayReportItem);
        map.put("userInfo", userFacade.queryByUuid(uuid));
        map.put("timeStr", strs + cal.getStr());
        return map;
    }


    public List<TaskCommon> getMyTask(Integer uuid, Integer taskId) {
        return mapper.queryTaskByUuidFromView(uuid, taskId);
    }

    /**
     * 我的  task
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param taskId
     * @param uuid
     */
    public Map<String, Object> selfRepairCard(Integer itemId, Integer itemContentId,
                                              Integer type, Integer taskId, Integer uuid) {
        Map<String, Object> map = new HashMap<>();
        String timeStr = "";
        String planTableName = TableUtil.getTaskPlanTableNameByItemType(type);
        String tableName = TableUtil.getTaskRecordTableNameByItemType(type);
        TaskCommon taskCommon = mapper.queryTaskFromTable(taskId, planTableName, type);
        Integer beginTime = taskCommon.getCreateTime() == null ? 1543457426 : taskCommon.getCreateTime();
        if (taskCommon != null) {
            timeStr = DateUtil.timestampToDates(taskCommon.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
        } else {
            timeStr = DateUtil.timestampToDates(1543457426, DateUtil.DATA_PATTON_YYYYMMDD2);
        }

        //用户该功课今日报数量  未删除
        Long dayNum = recordService.queryByUuidAndItemIdAndItemIdAndTypeAndTableName(taskId,
                0, DateUtil.getYMD(), tableName);
        //用户该功课本月报数量  未删除
        Long monthNum = recordService.queryByUuidAndItemIdAndItemIdAndTypeAndTableName(taskId,
                1, DateUtil.getMonth(), tableName);
        //用户该功课本年报数量  未删除
        Long yearNum = recordService.queryByUuidAndItemIdAndItemIdAndTypeAndTableName(taskId,
                2, DateUtil.getYear(), tableName);
        //用户该功课今日报数量  未删除
        Long totalNum = recordService.queryByUuidAndItemIdAndItemIdAndTypeAndTableName(taskId,
                4, DateUtil.getYMD(), tableName);

        //用户该功课本周报数量  未删除
        Long weekNum = recordService.queryByUuidAndItemIdAndItemIdAndTypeAndTableName(taskId,
                5, DateUtil.getTimesWeekmorning(), tableName);

        Integer reportCount = recordService.queryReportCount(tableName, uuid, taskId);

        map.put("myWork", 0);
        if (type == 1) {
            int count = lessonService.queryByCustomIdFromResourceCustom(itemId, uuid);
            map.put("myWork", count == 0 ? 0 : 1);
        }

        LessonCommon lesson = lessonService.queryLessonFromView(itemId, itemContentId, type);
        lesson.setCreateTimeStr(DateUtil.timestampToDates(lesson.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2) + "   " + DateUtil.dateToWeek(DateUtil.timestampToDates(lesson.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2), DateUtil.DATA_PATTON_YYYYMMDD2));
        map.put("beginTimeStr", timeStr + "   " + DateUtil.dateToWeek(timeStr, DateUtil.DATA_PATTON_YYYYMMDD2));
        map.put("joinDays", DateUtil.getTimeInterval(DateUtil.timestampToDates(beginTime, DateUtil.TIME_PATTON_DEFAULT)));
        map.put("dayNumStr", NumUtil.getTotalNumStr(new Double(dayNum == null ? 0 : dayNum), "0"));
        map.put("monthNumStr", NumUtil.getTotalNumStr(new Double(monthNum == null ? 0 : monthNum), "0"));
        map.put("yearNumStr", NumUtil.getTotalNumStr(new Double(yearNum == null ? 0 : yearNum), "0"));
        map.put("totalNumStr", NumUtil.getTotalNumStr(new Double(totalNum == null ? 0 : totalNum), "0"));
        map.put("weekNumStr", NumUtil.getTotalNumStr(new Double(weekNum == null ? 0 : weekNum), "0"));
        map.put("reportCount", reportCount == null ? 0 : reportCount);
        map.put("lesson", lesson);
        return map;
    }

    /**
     * 备份功课查找我所有功课任务
     *
     * @param userId
     * @return
     */
    public Map<String, Object> totalUserBackup(Integer userId) {

        Map<String, Object> map = new HashMap<>();

        TaskCommon taskCommon = mapper.totalUserBackup(userId);
        map.put("taskCommon", taskCommon);
        return map;
    }

    public Map<String, Object> exportExcel(Integer userId) {

        Map<String, Object> map = new HashMap<>();
        // 查找用户总计
        TaskCommon taskCommon = mapper.totalUserBackup(userId);

        map.put("count", taskCommon.getCount());
        map.put("doneNum", taskCommon.getDoneNum());

        try {
            int dayDown = DateUtil.daysBetween(taskCommon.getCreateTime(), DateUtil.currentSecond());
            map.put("timeStr", dayDown);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        map.put("nickName", taskCommon.getNickName());

        // 用户功课详细数据
        List<TaskCommon> taskCommonList = mapper.queryItemListDeatil(userId);
        //报数详细
        List<RecordCommon> recordCommonList = lessonService.queryItemRecordList(userId);
        map.put("recordCommonList", recordCommonList);
        map.put("taskCommonList", taskCommonList);
        map.put("taskCommon", taskCommon);
        return map;
    }

    public TaskCommon findBookTaskPlan(Integer itemId, Integer uuid) {
        return mapper.findBookTaskPlan(itemId, uuid);
    }

    public void updateBookcaseReport(Integer reportNum, String percent, Integer taskId, int currentSecond) {
        mapper.updateBookcaseReport(reportNum, percent, taskId, currentSecond);
    }

    public void updateCustomReport(long reportNum, int currentSecond, Integer taskId) {
        mapper.updateCustomReport(reportNum, currentSecond, taskId);
    }

    public void updateLessonReport(long reportNum, int currentSecond, Integer taskId) {
        mapper.updateLessonReport(reportNum, currentSecond, taskId);
    }


    public void updateIsExist(Integer type, Integer itemId, Integer itemContentId, Integer uuid, String tableName) {

        mapper.updateIsExist(type, itemId, itemContentId, uuid, tableName);
    }

    public void updateBookIsExist(Integer type, Integer itemId, Integer itemContentId, Integer uuid, String tableName) {

        mapper.updateBookIsExist(type, itemId, itemContentId, uuid, tableName);

    }

    public int queryCountByItemIdAndItemContentIdAndTableNameAndUuid(Integer type, Integer itemId, Integer itemContentId, Integer uuid, String tableName, Integer i) {

        int count = mapper.queryCountByItemIdAndItemContentIdAndTableNameAndUuid(type, itemId, itemContentId, uuid, tableName, i);
        return count;
    }

    public void insert(TaskCommon taskCommon, String tableName, Integer type) {

        mapper.insert(taskCommon, tableName, type);

    }


    public void updateDoneNumByTaskId(Integer taskId, Integer num, String appTaskTableName, Integer type, int isRemove) {
        mapper.updateDoneNumByTaskId(taskId, num, appTaskTableName, type, isRemove);
    }


    public Integer queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(Integer itemId, Integer itemContentId, Integer type, Integer uuid) {
        return mapper.queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(itemId, itemContentId, type, uuid);
    }


    ///////////////////////////////////////////////////////////////合并账号  更新数据///////////////////////////////////////////////////

    @Transactional
    @LcnTransaction
    public void mergeAccount(Integer uuid, Integer oldUuid) {
        mapper.updateUuidOfTask(uuid,oldUuid);
        recordService.updateUuidOfRecord(uuid,oldUuid);
        lessonService.updateUuidOfCustomItem(uuid,oldUuid);

    }


}
