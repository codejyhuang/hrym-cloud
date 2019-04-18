package com.everg.hrym.provider.flock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.flock.entity.FlockItemUserCount;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hrym13 on 2018/11/10.
 */
public interface FlockItemUserCountMapper {
    





    /**
     * 功课累计查询 groupby uuid
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
     * 查询用户功课 群 统计的 记录
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @return
     */
    @DS("slave")
    FlockItemUserCount queryByFlockIdAndItemIdAndItemContentIdAndTypeAndUuid(@Param("flockId") Integer flockId,
                                                                             @Param("itemId") Integer itemId,
                                                                             @Param("itemContentId") Integer itemContentId,
                                                                             @Param("type") Integer type,
                                                                             @Param("uuid") Integer uuid);

    void save(FlockItemUserCount flockItemUserCount);


    @DS("slave")
    FlockRecordDO queryByUuidFromItemCount(@Param("flockId") Integer flockId,
                                           @Param("itemId") Integer itemId,
                                           @Param("itemContentId") Integer itemContentId,
                                           @Param("type") Integer type,
                                           @Param("uuid") Integer userId);

    @DS("slave")
    Integer queryCountByItemIdAndContentIdAndType(@Param("type") Integer type,
                                                  @Param("itemId") Integer itemId,
                                                  @Param("itemContentId") Integer itemContentId);
    
    void insertFlockItemUser(FlockRecordDO recordDO);


    ///////合并账号
    void updateUuidOfFlockItemCount(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);

}
