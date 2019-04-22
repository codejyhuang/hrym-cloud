package com.everg.hrym.provider.pray.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.pray.entity.AssistPrayItemUserCount;
import com.everg.hrym.api.pray.entity.AssistPrayRecordDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hrym13 on 2018/11/10.
 */
@Repository
public interface PrayItemUserCountMapper {

    /**
     * 祈福  助念 用户功课统计表  添加
     *
     * @param assistPrayItemUserCount
     */
    void save(AssistPrayItemUserCount assistPrayItemUserCount);

    /**
     * 查询用户功课 群 统计的 记录
     *
     * @param assistPrayId  祈福/助念 id
     * @param itemId
     * @param itemContentId
     * @param type          功课类型
     * @return
     */
    @DS("slave")
    AssistPrayItemUserCount queryByAssistPrayIdAndItemIdAndItemContentIdAndTypeAndUuid(@Param("assistPrayId") Integer assistPrayId,
                                                                                       @Param("itemId") Integer itemId,
                                                                                       @Param("itemContentId") Integer itemContentId,
                                                                                       @Param("type") Integer type,
                                                                                       @Param("uuid") Integer uuid);
    /**
     * 功课累计查询 groupby uuid
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
     *  查询用户功课报数统计
     * @param assistPrayId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param userId
     * @return
     */
    @DS("slave")
    AssistPrayRecordDO queryByUuidFromItemCount(@Param("assistPrayId") Integer assistPrayId,
                                                @Param("itemId") Integer itemId,
                                                @Param("itemContentId") Integer itemContentId,
                                                @Param("type") Integer type,
                                                @Param("uuid") Integer userId);

}