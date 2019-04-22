package com.everg.hrym.provider.pray.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.pray.entity.AssistPray;
import com.everg.hrym.api.pray.entity.AssistPrayItem;
import com.everg.hrym.api.pray.entity.AssistPrayUserBack;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created  on 2018/11/07.
 */
@Repository
public interface PrayMapper {


    /**
     * 祈福  助念 创建
     *
     * @param assistPray
     */
    void create(AssistPray assistPray);

    /**
     * 祈福  助念 广场
     *
     * @param qo
     * @return
     */
    @DS("slave")
    List<AssistPray> assistPraySquare(QueryObjectParam qo);

    /**
     * 我发起的助念列表
     *
     * @param qo
     * @return
     */
    @DS("slave")
    List<AssistPray> sponsorAssistPray(QueryObjectParam qo);

    /**
     * 我参加的助念列表
     *
     * @param qo
     * @return
     */
    @DS("slave")
    List<AssistPray> joinAssistPrayList(QueryObjectParam qo);


    /**
     * 查询 祈福 / 助念群 的所有用书数量
     * @param id
     * @param programType
     * @return
     */
    @DS("slave")
    Integer queryCountByAssistPrayId(@Param("assistPrayId") Integer id, @Param("distinction") Integer programType);

    /**
     * 主键查询  祈福 / 助念
     * @param id
     * @return
     */
    @DS("slave")
    AssistPray queryByPrimaryKey(Integer id);

    /**
     * 更新 祈福 /助念  功课数量
     * @param id
     * @param i
     */
    void updateItemNum(@Param("id") Integer id, @Param("size") int i);

    /**
     * 根系 祈福 /助念  共修人数
     * @param id  祈福/助念id
     * @param num  更新的人数
     */
    void updateJoinNum(@Param("id") Integer id, @Param("num") int num);

    /**
     * 结束 祈福 /助念
     * @param id
     * @param currentSecond
     * @param state
     */
    void stopAssistPray(@Param("id") Integer id, @Param("overTime") int currentSecond, @Param("state") Integer state);

    /**
     * 更新 祈福 / 助念   简介/隐私/回响
     * @param assistPray
     */
    void updateIntroOrPrivacyOrDedicationVerses(AssistPray assistPray);

    /**
     * 修改任务完成状态
     * @param assistPray
     */
    @Update("update t_assist_pray SET state =0 WHERE id = #{assistPray}")
    void updateAssistPrayState(Integer assistPray);

    /**
     * 慧助念，祈福 ，群回向录入
     * @param assistPrayUserBack
     */
    @Insert(" INSERT INTO t_dedication_verses(item_type,type,time,flag,info,uuid,record_id,ymd,descJson,itemJson,distinction)\n" +
            "         VALUES\n" +
            "         (3,#{type},#{time},0,#{info},0,#{recordId},#{ymd},#{descJson},#{itemJson},#{distinction})")
    void insertAssistPraySpecialBack(AssistPrayUserBack assistPrayUserBack);

    /**
     * 查找慧助念，祈福 ，群里所有功课的名称
     * @param id
     * @return
     */
    @DS("slave")
    @Select(" SELECT m.day_done_num,w.lesson_name,m.item_id,m.item_content_id,m.type,m.unit,m.total_done_num\n" +
            "                   FROM t_assist_pray_item m\n" +
            "                    left JOIN t_flock_lesson_view w on w.item_id =m.item_id AND w.item_content_id = m.item_content_id AND w.type=m.type\n" +
            "                    WHERE  m.assist_pray_id=#{id} ")
    List<AssistPrayItem> queryByAssistPrayItemListByAssistPrayId(Integer id);

    @DS("slave")
    @Select("SELECT k.`name`,id,k.creater_id,k.dedication_verses,k.avatar_url,t.avatar,t.nickname,k.item_join_num,t.uuid,k.distinction,k.total_done_num\n" +
            "\n" +
            "                              FROM t_assist_pray k left JOIN t_user_account t ON t.uuid = k.creater_id\n" +
            "                              WHERE k.id =#{assistPray}")
    AssistPray querySumAssistPrayList(Integer assistPray);

    /**
     * 首页报数 查询功课列表
     * @param uuid
     * @return
     */
    List<AssistPrayItem> queryReportItemList(@Param("uuid") Integer uuid, @Param("distinction") Integer programType);

    /**
     * 根据UUID和群ID
     *
     * @param id
     * @return
     */
    @DS("slave")
    AssistPray listflockUserById(@Param("id") Integer id);
    
    @DS("slave")
    @Select("SELECT state,over_method FROM t_assist_pray WHERE id =#{assistPrayId}")
    AssistPray queryAssistPray(Integer assistPrayId);
}


