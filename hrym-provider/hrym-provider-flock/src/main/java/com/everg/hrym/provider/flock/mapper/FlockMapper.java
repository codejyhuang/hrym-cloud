package com.everg.hrym.provider.flock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.flock.entity.Flock;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created  on 2018/11/07.
 */
public interface FlockMapper {


    /**
     * 查询共修群列表
     *
     * @param uuid
     * @return
     */
    @DS("slave")
    List listByIds(Integer uuid);


    void createFlock(Flock flock);

    /**
     * 根据UUID和群ID
     *
     * @param uuid
     * @param flockId
     * @return
     */
    @DS("slave")
    FlockUser listflockUserById(@Param("uuid") Integer uuid, @Param("flockId") Integer flockId);

    /**
     * 根据群ID查找信息
     *
     * @param id
     * @return
     */
    @DS("slave")
    Flock queryByPrimaryKey(Integer id);


    /**
     * 逻辑解散共修群   将共修群 状态设置为 已解散
     *
     * @param flockId
     */
    void logicDeleteFlock(@Param("flockId") Integer flockId, @Param("state") Integer state);

    /**
     * 编辑 / 更新 群简介
     *
     * @param intro
     */
    void updateFlockIntro(@Param("id") Integer id, @Param("intro") String intro);

    /**
     * 编辑 群隐私
     *
     * @param id
     * @param privacy
     */
    void updateFlockPrivacy(@Param("id") Integer id, @Param("privacy") Integer privacy);

    /**
     * 编辑 群隐私
     *
     * @param id
     * @param dedicationVerses
     */
    void updateFlockDedicationVerses(@Param("id") Integer id, @Param("de") String dedicationVerses);


    /**
     * 更新共修群功课数量
     *
     * @param id
     * @param size
     */
    void updateFlockItemNum(@Param("id") Integer id, @Param("size") int size);

    /**
     * 更新群 共修人数
     *
     * @param id
     * @param updateNum
     */
    void updateFlockJoinNum(@Param("id") Integer id, @Param("updateNum") int updateNum);


    @DS("slave")
    List<FlockItem> queryFlockItemList(Integer uuid);

    /**
     * 查询时否有同名功课
     *
     * @param name
     * @return
     */
    @DS("slave")
    Integer queryCountByName(String name);

    @DS("slave")
    Flock queryFlockCreatedTimeById(Integer id);

    @DS("slave")
    List<Flock> queryByItemIdAndContentIdAndUuid(@Param("itemId") Integer itemId,
                                                 @Param("itemContentId") Integer itemContentId,
                                                 @Param("type") Integer type,
                                                 @Param("userId") Integer userId);

    @DS("slave")
    List<Flock> queryJoinFlockList(@Param("uuid") Integer uuid, @Param("keywords") String keywords, @Param("type") Integer type);

    @DS("slave")
    List<Flock> queryFlockList();


    void updateUuidOfFlock(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);


}
