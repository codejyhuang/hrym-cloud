package com.everg.hrym.provider.flock.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockObjectDO;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import com.everg.hrym.api.flock.entity.ParseRecord;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import com.everg.hrym.provider.flock.mapper.FlockRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlockRecordService {

    @Autowired
    private FlockRecordMapper mapper;

    @Autowired
    private ParseRecordService parseRecordService;

    /**
     * 删除 群 record 记录
     *
     * @param recordId
     * @param year
     * @return
     */
    @Transactional
    @LcnTransaction
    public void removeFlockRecord(Integer recordId, Integer year) {
        //逻辑删除  小程序上报记录
        mapper.logicRemoveByPrimaryKey(recordId, "t_flock_record_" + year);
    }

    /**
     * 动态点赞
     *
     * @param recordId
     * @return
     */
    public List<ParseRecord> queryLikeMember(Integer recordId) {
        return parseRecordService.queryLikeMember(recordId, 0);
    }


    /**
     * 动态点赞
     *
     * @param id
     * @param year
     * @param uuid
     * @param type
     * @return
     */
    @Transactional
    public void parseRecord(Integer id, Integer year, Integer uuid, Integer type) {
        String tableName = "t_flock_record_" + year;
        if (type.equals(1)) {   // 1:点赞   0:取消赞
            //点赞操作
            ParseRecord ps = parseRecordService.queryByRelationIdAndRecordTypeAndFrom(id, //记录的id  recordId
                    uuid, 0);
            if (ps == null) {
                ps = new ParseRecord();
                ps.setFrom(uuid);
                ps.setTime(DateUtil.currentSecond());
                ps.setRelationId(id.toString());
                ps.setRecordType(0);
                parseRecordService.insert(ps);
                //更新record
                mapper.updateLikeNumByPrimaryKey(id, tableName, 1);
            }
        } else {
            //取消赞
            int rows = parseRecordService.cancelByRelationIdAndUuid(id, uuid, 0);
            //更新record
            mapper.updateLikeNumByPrimaryKey(id, tableName, -rows);
        }
    }

    public List<FlockRecordDO> queryByFlockId(Integer id, List<FlockItem> lessonDOList, Integer uUid, String tableName1, String tableName2) {
        return mapper.queryByFlockId(id, lessonDOList, uUid, tableName1, tableName2);
    }

    public List<FlockRecordVo> findCustomSum(FlockRecordVo vo, String tableName, String tableName1, Integer year) {
        return mapper.findCustomSum(vo, tableName, tableName1, year);
    }

    public List<FlockRecordVo> queryByCUstomIds(FlockRecordVo vo, String tableName, String tableName1, Integer year) {
        return mapper.queryByCUstomIds(vo, tableName, tableName1, year);
    }

    public List<FlockRecordVo> findLessonSum(FlockRecordVo vo, String tableName, String tableName1, Integer year) {
        return mapper.findLessonSum(vo, tableName, tableName1, year);
    }

    public List<FlockRecordVo> queryByIds(FlockRecordVo vo, String tableName, String tableName1, Integer year) {
        return mapper.queryByIds(vo, tableName, tableName1, year);
    }

    public List<FlockRecordVo> queryDayNumByFlockId(Integer id, Integer type, String tableName, String tableName1) {
        return mapper.queryDayNumByFlockId(id, type, tableName, tableName1);
    }

    public FlockRecordVo queryLastFlockNum(Integer id, Integer createTime, Integer type, String tableName) {
        return mapper.queryLastFlockNum(id, createTime, type, tableName);
    }

    public List<FlockRecordVo> queryRecordListByTime(Integer type, Integer id, Integer createTime, String tableName) {
        return mapper.queryRecordListByTime(type, id, createTime, tableName);
    }

    public FlockRecordVo queryLastMyNum(Integer id, Integer createTime, Integer type, Integer uUid, String tableName) {
        return mapper.queryLastMyNum(id, createTime, type, uUid, tableName);
    }

    public Integer queryFlockItemNum(Integer id, Integer type, Integer createTime, String tableName) {
        return mapper.queryFlockItemNum(id, type, createTime, tableName);
    }

    public FlockObjectDO flockWeekNumDetailByFlockId(Integer uUid, Integer id, Integer thisWeek, Integer lastWeek, String tableName) {
        return mapper.flockWeekNumDetailByFlockId(uUid, id, thisWeek, lastWeek, tableName);
    }

    public void insertFlockRecord(List<FlockRecordDO> list, String tableName) {
        mapper.insertFlockRecord(list, tableName);
    }

    public List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndType(Integer flockId, Integer itemId, Integer itemContentId, Integer type, Integer userId) {
        return mapper.queryByFlockIdAndItemIdAndItemContentIdAndType(flockId, itemId, itemContentId, type, userId);
    }

    public List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndTimeType(Integer flockId, Integer itemId, Integer itemContentId, Integer type, Integer timeType, String tableName, Integer ymd, Integer year, Integer month, int timesWeekmorning) {
        return mapper.queryByFlockIdAndItemIdAndItemContentIdAndTimeType(flockId, itemId, itemContentId, type, timeType, tableName, ymd, year, month, timesWeekmorning);
    }

    public FlockRecordDO queryByUuid(Integer flockId, Integer itemId, Integer itemContentId, Integer type, String tableName, Integer year, Integer ymd, Integer month, Integer timeType, Integer userId, int timesWeekmorning) {
        return mapper.queryByUuid(flockId, itemId, itemContentId, type, tableName, year, ymd, month, timeType, userId, timesWeekmorning);
    }

    public Integer queryflockTotalNum(Integer id, Integer type, Integer createTime) {
        return mapper.queryflockTotalNum(id, type, createTime);
    }

    public Integer queryMyTotalNum(Integer uUid, Integer id) {
        return mapper.queryMyTotalNum(uUid, id);
    }

    public FlockRecordVo queryYearNumByFlockIdAndTableName(Integer flockId, String tableName) {
        return mapper.queryYearNumByFlockIdAndTableName(flockId, tableName);
    }

    public List<FlockRecordVo> queryIsNotMyNum(Integer uuid, Integer type, Integer flockId, Integer createTime, String tableName, String tableName1) {
        return mapper.queryIsNotMyNum(uuid, type, flockId, createTime, tableName, tableName1);
    }

    //合并账号
    public void updateUuidOfFlockRecord(Integer uuid, Integer oldUuid) {
        mapper.updateUuidOfFlockRecord(uuid,oldUuid);
    }
}
