package com.everg.hrym.provider.flock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlockUserMapper {

    /**
     * 插入 群 -- 用户 关系
     * @param flockUser
     */
    void insert(FlockUser flockUser);

    /**
     * 查询共修群用户
     * @param id
     * @return
     */
    @DS("slave")
    List<FlockUser> queryByFlockId(@Param("id") Integer id, @Param("type") Integer type, @Param("keywords") String keywords);


    /**
     * 批量删除 群成员
     * @param flockId
     * @param userIds
     */
    void batchRemoveFlockUser(@Param("flockId") Integer flockId, @Param("userIds") List<Integer> userIds);


    /**
     * 查询共修群用户
     * @param id
     * @return
     */
    @DS("slave")
    List<FlockUser> selectAllByFlockId(Integer id);

    /**
     * 查询群所有用户数量
     * @param flockId
     * @return
     */
    @DS("slave")
    Integer queryCountByFlockId(Integer flockId);

    /**
     *
     * @param flockId
     * @param uuid
     * @return
     */
    @DS("slave")
    FlockItem queryByFlockIdAndUuid(@Param("flockId") Integer flockId, @Param("uuid") Integer uuid);

    /**
     * 查询是否在群里
     * @param id
     * @param uUid
     * @return
     */
    @DS("slave")
    FlockUser queryCountByFlockIdAndUuid(@Param("flockId") Integer id, @Param("uuid") Integer uUid);

    /**
     * 更新群排序 order
     * @param flockId
     * @param order
     * @param uuid
     */
    void updateFlockUserOrder(@Param("flockId") Integer flockId, @Param("order") Integer order, @Param("uuid") Integer uuid);

    /**
     * 查询群所有用户 id 集合
     * @param id
     * @return
     */
    @DS("slave")
    List<Integer> queryUuidListByFlockId(Integer id);


    /////////合并账号 更新数据
    void updateUuidOfFlockUser(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);
}
