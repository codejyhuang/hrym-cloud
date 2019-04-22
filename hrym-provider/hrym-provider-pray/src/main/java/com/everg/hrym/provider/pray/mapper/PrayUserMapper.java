package com.everg.hrym.provider.pray.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.pray.entity.AssistPrayUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrayUserMapper {

    /**
     * 插入 群 -- 用户 关系
     * @param assistPrayUser
     */
    void insert(AssistPrayUser assistPrayUser);


    /**
     * 查询 祈福  助念  的群人数
     * @param id
     * @return
     */
    @DS("slave")
    Integer queryCountByAssistPrayId(Integer id);

    /**
     * 查询祈福用户
     * @param assistPrayId  祈福/助念 id
     * @param uuid  用户id
     * @return
     */
    @DS("slave")
    AssistPrayUser queryCountByFlockIdAndUuid(@Param("assistPrayId") Integer assistPrayId, @Param("uuid") Integer uuid);

    /**
     * 批量删除  祈福 助念 群成员
     * @param assistPrayId
     * @param userIds
     */
    void batchRemoveAssistPrayUser(@Param("assistPrayId") Integer assistPrayId, @Param("userIds") List<Integer> userIds);

    /**
     * 
     * @param assistPrayId
     * @param uuid
     * @param distinction
     * @return
     */
    @DS("slave")
    AssistPrayUser queryByAssistPrayIdAndUuid(@Param("assistPrayId") Integer assistPrayId,
                                              @Param("uuid") Integer uuid,
                                              @Param("distinction") Integer distinction);

    /**
     * 群成员 所以有uuid
     * @param id
     * @return
     */
    @DS("slave")
    List<Integer> queryUuidListByAssistPrayId(Integer id);

    /**
     * 查询用户  列表
     * @param assistPrayId  助念/祈福 id
     * @param roleAdmin   查询用户的角色
     * @param keywords   条件查询的关键字
     * @return
     */
    @DS("slave")
    List<AssistPrayUser> queryByAssistPrayId(@Param("assistPrayId") Integer assistPrayId, @Param("role") Integer roleAdmin, @Param("keywords") String keywords);
    
    /**
     * 判断是否加群
     * @param id
     * @param uuid
     * @return
     */
    @DS("slave")
    @Select(" SELECT COUNT(1) FROM t_assist_pray_user WHERE assist_pray_id = #{id} and uuid =#{uuid} ")
    Integer queryIsAddAssistPrayUser(@Param("id") Integer id, @Param("uuid") Integer uuid);
    
    @DS("slave")
    @Select("SELECT COUNT(*) as count FROM t_assist_pray_user WHERE uuid =#{uuid} and distinction = #{programType}")
    Integer queryAssistPrayUserBYUuId(@Param("uuid") Integer uuid, @Param("programType") Integer programType);
    
    @Insert(" INSERT INTO t_qr_code_record (uuid,scene,create_time,distinction)VALUES(#{uuid},#{scene},#{time},#{distinction})")
    void saveWXACodeUrl(@Param("uuid") Integer userId,
                        @Param("scene") String scene,
                        @Param("time") Integer time,
                        @Param("distinction") Integer distinction);
    
    /**
     * 查找用户扫描记录
     * @param scene
     * @param userId
     * @return
     */
    @DS("slave")
    @Select(" SELECT COUNT(*) as count from t_qr_code_record WHERE scene = #{scene} AND distinction = #{distinction} AND uuid =#{uuid} ")
    Integer findWXACodeUrl(@Param("scene") String scene, @Param("uuid") Integer userId, @Param("distinction") Integer distinction);
    
    
}
