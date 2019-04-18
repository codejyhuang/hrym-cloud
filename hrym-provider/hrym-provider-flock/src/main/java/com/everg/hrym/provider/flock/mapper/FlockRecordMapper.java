package com.everg.hrym.provider.flock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockObjectDO;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hrym13 on 2018/11/9.
 */
public interface FlockRecordMapper {
    
    /**
     * 添加修行记录<慧共修>
     *
     * @param list
     */
    void insertFlockRecord(@Param("list") List<FlockRecordDO> list,
                           @Param("tableName") String tableName);
    


    /**
     * 查询群的 修行轨迹
     *
     * @param id
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockId(@Param("id") Integer id, @Param("list") List<FlockItem> flockItemList,
                                       @Param("uuid") Integer uuid, @Param("tableName1") String tableName1, @Param("tableName2") String tableName2);


    /**
     * 查询共修群 动态
     *
     * @param id
     * @param tableName
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockId(@Param("id") Integer id, @Param("tableName") String tableName);

    @DS("slave")
    List<FlockRecordVo> queryByIds(@Param("vo") FlockRecordVo vo,
                                   @Param("tableName") String tableName,
                                   @Param("tableName1") String tableName1,
                                   @Param("years") Integer year);

    @DS("slave")
    List<FlockRecordVo> findLessonSum(@Param("vo") FlockRecordVo vo,
                                      @Param("tableName") String tableName,
                                      @Param("tableName1") String tableName1,
                                      @Param("year") Integer year);

    @DS("slave")
    List<FlockRecordVo> queryByCUstomIds(@Param("vo") FlockRecordVo vo,
                                         @Param("tableName") String tableName,
                                         @Param("tableName1") String tableName1,
                                         @Param("years") Integer year);

    @DS("slave")
    List<FlockRecordVo> findCustomSum(@Param("vo") FlockRecordVo vo,
                                      @Param("tableName") String tableName,
                                      @Param("tableName1") String tableName1,
                                      @Param("year") Integer year);


    /**
     * 功课动态查询修行记录
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndType(@Param("flockId") Integer flockId,
                                                                       @Param("itemId") Integer itemId,
                                                                       @Param("itemContentId") Integer itemContentId,
                                                                       @Param("type") Integer type,
                                                                       @Param("userId") Integer userId);

    /**
     * 功课 sum 根据时间类型 统计查询 修行记录
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param timeType
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndTypeGroupBy(@Param("flockId") Integer flockId,
                                                                              @Param("itemId") Integer itemId,
                                                                              @Param("itemContentId") Integer itemContentId,
                                                                              @Param("type") Integer type,
                                                                              @Param("timeType") Integer timeType);

    /**
     * 查询今日功课记录 group by uuid
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndTypeAndToday(@Param("flockId") Integer flockId,
                                                                               @Param("itemId") Integer itemId,
                                                                               @Param("itemContentId") Integer itemContentId,
                                                                               @Param("type") Integer type,
                                                                               @Param("tableName") String tableName,
                                                                               @Param("ymd") Integer ymd
    );

    /**
     * 查询本年 或者 本月的功课上报记录  group by uuid
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndTypeAndMonthOrYear(@Param("flockId") Integer flockId,
                                                                                     @Param("itemId") Integer itemId,
                                                                                     @Param("itemContentId") Integer itemContentId,
                                                                                     @Param("type") Integer type,
                                                                                     @Param("tableName") String tableName,
                                                                                     @Param("year") Integer year,
                                                                                     @Param("month") Integer month);


    /**
     * 查询本用户的  累计报数数据
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param tableName
     * @param year
     * @param ymd
     * @param timeType
     * @return
     */
    @DS("slave")
    FlockRecordDO queryByUuid(@Param("flockId") Integer flockId,
                              @Param("itemId") Integer itemId,
                              @Param("itemContentId") Integer itemContentId,
                              @Param("type") Integer type,
                              @Param("tableName") String tableName,
                              @Param("year") Integer year,
                              @Param("ymd") Integer ymd,
                              @Param("month") Integer month,
                              @Param("timeType") Integer timeType,
                              @Param("uuid") Integer uuid,
                              @Param("weekMorning") Integer weekMorning);

    /**
     * 查询  年 月 日 分组的数据
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param tableName
     * @param ymd
     * @param year
     * @param month
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndTimeType(@Param("flockId") Integer flockId,
                                                                           @Param("itemId") Integer itemId,
                                                                           @Param("itemContentId") Integer itemContentId,
                                                                           @Param("type") Integer type,
                                                                           @Param("timeType") Integer timeType,
                                                                           @Param("tableName") String tableName,
                                                                           @Param("ymd") Integer ymd,
                                                                           @Param("year") Integer year,
                                                                           @Param("month") Integer month,
                                                                           @Param("weekMorning") Integer weekMorning);


    void insertFlockRecordByFlockId(@Param("record") FlockRecordDO record, @Param("tableName") String tableName);

    /**
     * 针对功课  查询用户报数量
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param userId
     * @return
     */
    @DS("slave")
    Double queryItemNum(@Param("itemId") Integer itemId,
                        @Param("itemContentId") Integer itemContentId,
                        @Param("type") Integer type,
                        @Param("userId") Integer userId,
                        @Param("tableName") String tableName,
                        @Param("format") Integer format,
                        @Param("timeType") Integer timeType,
                        @Param("state") Integer state);


    /**
     * 查询用户  功课   上报记录
     * @param itemId
     * @param itemContentId
     * @param type
     * @param userId
     * @param state
     * @return
     */
    @DS("slave")
    List<FlockRecordDO> queryRecordByItemIdAndItemContentIdAndTypeAndUuid(@Param("itemId") Integer itemId,
                                                                          @Param("itemContentId") Integer itemContentId,
                                                                          @Param("type") Integer type,
                                                                          @Param("userId") Integer userId,
                                                                          @Param("state") Integer state);



//    void insertHistoryFlockRecord(@Param("li") FlockCountReportParam param, @Param("tableName") String tableName);

    /**
     * 逻辑删除 record 记录
     * @param recordId
     */
    int logicRemoveByPrimaryKey(@Param("recordId") Integer recordId, @Param("tableName") String tableName);

    /**
     *
     * @param id
     * @param i
     */
    void updateLikeNumByPrimaryKey(@Param("id") Integer id, @Param("tableName") String tableName, @Param("count") int i);

    @DS("slave")
    FlockObjectDO flockWeekNumDetailByFlockId(@Param("uuid") Integer uUid,
                                              @Param("id") Integer id,
                                              @Param("thisWeek") Integer thisWeek,
                                              @Param("lastWeek") Integer lastWeek,
                                              @Param("tableName") String tableName);

    /**
     * 每日群数据
     * @param id
     * @param tableName
     *@param tableName1 @return
     */
    @DS("slave")
    List<FlockRecordVo> queryDayNumByFlockId(@Param("id") Integer id, @Param("type") Integer type,
                                             @Param("tableName") String tableName,
                                             @Param("tableName1") String tableName1);

    @DS("slave")
    FlockRecordDO queryMydayNumByFlockId(@Param("id") Integer id, @Param("uuid") Integer uUid);

    @DS("slave")
    FlockRecordDO queryMaxdayNumByFlockId(Integer id);

    @DS("slave")
    List<FlockRecordVo> queryWeekNumByFlockId(Integer id);

    @DS("slave")
    FlockRecordDO queryMyWeekNumByFlockId(@Param("uuid") Integer uUid, @Param("id") Integer id);

    @DS("slave")
    FlockRecordDO queryMaxWeekNumByFlockId(Integer id);

    @DS("slave")
    List<FlockRecordVo> queryMonthNumByFlockId(Integer id);

    @DS("slave")
    FlockRecordDO queryMyMonthNumByFlockId(@Param("uuid") Integer uUid, @Param("id") Integer id);

    @DS("slave")
    FlockRecordDO queryMaxMonthNumByFlockId(Integer id);

    @DS("slave")
    List<FlockRecordVo> queryYearNumByFlockId(Integer id);

    @DS("slave")
    FlockRecordDO queryMyYearNumByFlockId(@Param("uuid") Integer uUid, @Param("id") Integer id);

    @DS("slave")
    FlockRecordDO queryMaxYearNumByFlockId(Integer id);

    @DS("slave")
    List<FlockRecordVo> queryRecordListByTime(@Param("type") Integer type,
                                              @Param("id") Integer id,
                                              @Param("time") Integer time,
                                              @Param("tableName") String tableName);

    @DS("slave")
    Integer queryFlockItemNum(@Param("id") Integer id,
                              @Param("type") Integer type,
                              @Param("createTime") Integer createTime,
                              @Param("tableName") String tableName);

    @DS("slave")
    FlockRecordVo queryLastFlockNum(@Param("id") Integer id,
                                    @Param("createTime") Integer createTime,
                                    @Param("type") Integer type,
                                    @Param("tableName") String tableName);

    @DS("slave")
    FlockRecordVo queryLastMyNum(@Param("id") Integer id,
                                 @Param("createTime") Integer createTime,
                                 @Param("type") Integer type,
                                 @Param("uuid") Integer uuid,
                                 @Param("tableName") String tableName);


    @DS("slave")
    Integer queryflockTotalNum(@Param("id") Integer id,
                               @Param("type") Integer type,
                               @Param("createTime") Integer createTime);

    @DS("slave")
    Integer queryMyTotalNum(@Param("uuid") Integer uUid, @Param("id") Integer id);

    @DS("slave")
    FlockRecordVo queryYearNumByFlockIdAndTableName(@Param("flockId") Integer flockId, @Param("tableName") String tableName);

    @DS("slave")
    List<FlockRecordVo> queryIsNotMyNum(@Param("uuid") Integer uuid,
                                        @Param("type") Integer type,
                                        @Param("flockId") Integer flockId,
                                        @Param("createTime") Integer createTime,
                                        @Param("tableName") String tableName,
                                        @Param("tableName1") String tableName1);


    void updateUuidOfFlockRecord(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);

}