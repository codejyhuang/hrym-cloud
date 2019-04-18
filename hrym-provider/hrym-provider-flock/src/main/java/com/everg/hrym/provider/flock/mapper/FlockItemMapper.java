package com.everg.hrym.provider.flock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FlockItemMapper {

    /**
     * 添加群功课 用户的上报统计
     *
     * @param flockItem
     */
    void insert(FlockItem flockItem);

    @DS("slave")
    List<FlockItem> queryByFlockId(@Param("flockId") Integer id);


    @DS("slave")
    List<FlockItem> selectByFlockId(Integer id);


    @DS("slave")
    FlockItem selectFlockItemList(@Param("itemContentId") Integer itemContentId,
                                  @Param("contentTable") String contentTable);


    /**
     * 查询是否有加过该功课
     *
     * @param id
     * @param itemId
     * @param itemContentId
     * @param type
     */
    @DS("slave")
    FlockItem queryByFlockIdAndItemIdAndContentIdAndType(@Param("flockId") Integer id,
                                                         @Param("itemId") Integer itemId,
                                                         @Param("itemContentId") Integer itemContentId,
                                                         @Param("type") Integer type);


    /**
     * 更新群功课状态
     *
     * @param flockItem
     */
    void updateFlockItem(FlockItem flockItem);


    /**
     * 更新群功课状态
     *
     * @param id
     * @param i
     */
    void updateStateByFlockId(@Param("flockId") Integer id, @Param("state") int i);

    @DS("slave")
    List<FlockItem> queryByFlockItemListByFlockId(Integer flockId);


    /**
     * 查找群功课
     *
     * @param id
     * @return
     */
    @DS("slave")
    List<FlockItem> queryByFlockIdJoinView(@Param("id") Integer id, @Param("uuid") Integer uuid);

    @DS("slave")
    List<FlockItem> queryByFlockItemByFlockId(Integer flockId);

    @DS("slave")
    List<FlockItem> queryFlockItemNameByFlockId(Integer flockId);


    /**
     * 逻辑删除群功课
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param state
     * @return
     */
    int batchLogicRemoveFlockLesson(@Param("flockId") Integer flockId,
                                    @Param("itemId") Integer itemId,
                                    @Param("itemContentId") Integer itemContentId,
                                    @Param("type") Integer type,
                                    @Param("state") int state);


    @DS("slave")
    List<Integer> getFlockIdByItemAndUuid(@Param("itemId") Integer itemId,
                                          @Param("type") Integer type,
                                          @Param("itemContentId") Integer itemContentId,
                                          @Param("uuid") Integer uuid);



    @DS("slave")
    List<Map<String,Object>> queryFlockMessage(@Param("list") List<FlockItem> list, @Param("flockId") Integer flockId);

    @DS("slave")
    List<Map<String,Object>> queryFlockItemUserCount(@Param("list") List<FlockItem> flockItemName,@Param("flockId") Integer flockId);

    @DS("slave")
    Map<String,Object> queryFlockItemToTal(@Param("list") List<FlockItem> flockItemName, @Param("flockId") Integer flockId);

    @DS("slave")
    FlockRecordDO getFlockDedicationNum(@Param("flockId") Integer flockId, @Param("uuid") Integer uuid);
}
