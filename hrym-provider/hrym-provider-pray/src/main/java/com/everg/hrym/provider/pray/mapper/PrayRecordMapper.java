package com.everg.hrym.provider.pray.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.pray.entity.AssistPrayItem;
import com.everg.hrym.api.pray.entity.AssistPrayRecordDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hrym13 on 2018/11/9.
 */
@Repository
public interface PrayRecordMapper {
    /**
     * 助念 / 祈福 群详情 上报记录
     *
     * @param id
     * @param itemList
     * @param uuid
     * @return
     */
    @DS("slave")
    List<AssistPrayRecordDO> queryByAssistPrayId(@Param("id") Integer id, @Param("itemList") List<AssistPrayItem> itemList, @Param("uuid") Integer uuid);



    /**
     * 功课动态查询修行记录
     *
     * @param assistPrayId
     * @param itemId
     * @param itemContentId
     * @param type
     * @return
     */
    @DS("slave")
    List<AssistPrayRecordDO> queryByAssistPrayIdAndItemIdAndItemContentIdAndType(@Param("assistPrayId") Integer assistPrayId,
                                                                                 @Param("itemId") Integer itemId,
                                                                                 @Param("itemContentId") Integer itemContentId,
                                                                                 @Param("type") Integer type,
                                                                                 @Param("userId") Integer userId);


    /**
     * 查询  年 月 日 分组的数据
     * @param assistPrayId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param timeType
     * @param tableName
     * @param ymd
     * @param year
     * @param month
     * @return
     */
    @DS("slave")
    List<AssistPrayRecordDO> queryByAssistPrayIdAndItemIdAndItemContentIdAndTimeType(@Param("assistPrayId") Integer assistPrayId,
                                                                                     @Param("itemId") Integer itemId,
                                                                                     @Param("itemContentId") Integer itemContentId,
                                                                                     @Param("type") Integer type,
                                                                                     @Param("timeType") Integer timeType,
                                                                                     @Param("tableName") String tableName,
                                                                                     @Param("ymd") Integer ymd,
                                                                                     @Param("year") Integer year,
                                                                                     @Param("month") Integer month);

    /**
     * 查询本用户的  年 月 日 分组的数据 累计报数数据
     * @param assistPrayId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param tableName
     * @param year
     * @param ymd
     * @param month
     * @param timeType
     * @param uuid
     * @return
     */
    AssistPrayRecordDO queryByUuid(@Param("assistPrayId") Integer assistPrayId,
                                   @Param("itemId") Integer itemId,
                                   @Param("itemContentId") Integer itemContentId,
                                   @Param("type") Integer type,
                                   @Param("tableName") String tableName,
                                   @Param("year") Integer year,
                                   @Param("ymd") Integer ymd,
                                   @Param("month") Integer month,
                                   @Param("timeType") Integer timeType,
                                   @Param("uuid") Integer uuid);

    /**
     * 更新  record 点赞人数
     * @param id
     * @param tableName
     * @param i
     */
    void updateLikeNumByPrimaryKey(@Param("id") Integer id, @Param("tableName") String tableName, @Param("count") int i);
}