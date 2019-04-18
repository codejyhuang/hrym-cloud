package com.everg.hrym.provider.lesson.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.flock.facade.FlockItemFacade;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import com.everg.hrym.api.user.facade.UserFacade;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.TableUtil;
import com.everg.hrym.provider.lesson.mapper.RecordCommonMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/record/")
public class RecordService {
    @Autowired
    private RecordCommonMapper mapper;

    @Autowired
    private ItemUserUnitService userUnitService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private FlockItemFacade flockItemFacade;

    /**
     * record 集合
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param taskId
     */
    public Map<String, Object> selfRecordHistory(Integer itemId, Integer itemContentId, Integer type,
                                                 Integer uuid, Integer currentPage, Integer pageSize,
                                                 Integer taskId) {
        Map<String, Object> map = new HashMap<>();
        //查询用户功课量词
        String unit = userUnitService.queryUnitByUuidAndItemIdAndItemContentIdAndType(uuid, itemId, itemContentId, type);
        String tableName = TableUtil.getTaskRecordTableNameByItemType(type);
        PageHelper.startPage(currentPage, pageSize);
        List<RecordCommon> appRecordCommons = mapper.queryRecordByTaskId(taskId, tableName);
        PageInfo pageInfo = new PageInfo(appRecordCommons);
        map.put("recordList", appRecordCommons);
        map.put("total", pageInfo.getTotal());
        map.put("totalPage", pageInfo.getPages());
        map.put("hasNextPage", pageInfo.isHasNextPage());
        map.put("userInfo", userFacade.queryByUuid(uuid));
        map.put("unit", unit == null ? "遍" : unit);
        return map;
    }

    /**
     * 删除record 记录
     *
     * @param taskId
     * @param type
     * @param recordId
     * @param num
     * @param ymd
     */
    @Transactional
    @LcnTransaction
    public void removeSelfRecordHistory(Integer taskId,
                                        Integer type,
                                        Integer recordId,
                                        Integer num,
                                        String ymd,
                                        Integer uuid, Integer year) {
        String appRecordTableName = TableUtil.getTaskRecordTableNameByItemType(type);
        String appTaskTableName = TableUtil.getTaskPlanTableNameByItemType(type);
        //删除 app 中的record
        mapper.removeByPrimaryKey(recordId, appRecordTableName);
        //更新 task 中的任务完成数量
        taskService.updateDoneNumByTaskId(taskId, num, appTaskTableName, type, DateUtil.timestampToDates(DateUtil.currentSecond(), DateUtil.DATA_PATTON_YYYYMMDD).equals(ymd) ? 1 : 0);

        flockItemFacade.removeFlockRecord(recordId, year);

    }

    public Long queryByUuidAndItemIdAndItemIdAndTypeAndTableName(Integer id, int i, Integer ymd, String tableName) {
        return mapper.queryByUuidAndItemIdAndItemIdAndTypeAndTableName(id, i, ymd, tableName);
    }

    public Integer queryReportCount(String tableName, Integer userId, Integer id) {
        return mapper.queryReportCount(tableName, userId, id);
    }

    public Integer queryBookHistoryRecord(Integer taskId) {
        return mapper.queryBookHistoryRecord(taskId);
    }

    public void insertBookRecord(RecordCommon record) {
        mapper.insertBookRecord(record);
    }

    public void updateBookHistoryRecordNum(Integer reportNum, Integer taskId) {
        mapper.updateBookHistoryRecordNum(reportNum, taskId);
    }

    public Long queryCustomHistoryRecordNum(Integer taskId) {
        return mapper.queryCustomHistoryRecordNum(taskId);
    }

    public void saveCustomRecord(RecordCommon custom) {
        mapper.saveCustomRecord(custom);
    }

    public void updateCustomHistoryRecordNum(long reportNum, Integer taskId) {
        mapper.updateCustomHistoryRecordNum(reportNum, taskId);
    }

    public Long queryLessonHistoryRecordNum(Integer taskId) {
        return mapper.queryLessonHistoryRecordNum(taskId);
    }

    public void saveLessonRecord(RecordCommon lesson) {
        mapper.saveLessonRecord(lesson);
    }

    public void updateLessonHistoryRecordNum(long reportNum, Integer taskId) {
        mapper.updateLessonHistoryRecordNum(reportNum, taskId);
    }


    ///////////////////////////////////////////////用户合并账号 更新数据///////////////////////////////////
    public void updateUuidOfRecord(Integer uuid, Integer oldUuid) {
        mapper.updateUuidOfRecord(uuid,oldUuid);
    }
}
