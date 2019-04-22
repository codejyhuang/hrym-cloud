package com.everg.hrym.provider.pray.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.pray.entity.AssistPrayItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrayItemMapper {

    /**
     * 祈福 助念  关系表维护
     *
     * @param assistPrayItem
     */
    void insert(AssistPrayItem assistPrayItem);

    /**
     * 查询 祈福 助念 功课列表
     *
     * @param assistPrayId 祈福  助念 id
     * @return
     */
    @DS("slave")
    List<AssistPrayItem> queryByAssistPrayId(Integer assistPrayId);

    /**
     * 查询是否有加过该功课
     *
     * @param id
     * @param itemId
     * @param itemContentId
     * @param type
     */
    @DS("slave")
    AssistPrayItem queryByAssistPrayIdIdAndItemIdAndContentIdAndType(@Param("assistPrayId") Integer id,
                                                                     @Param("distinction") Integer distinction,
                                                                     @Param("itemId") Integer itemId,
                                                                     @Param("itemContentId") Integer itemContentId,
                                                                     @Param("type") Integer type);
    
    //查找助念，祈福群ID
    @DS("slave")
    @Select("select m.assist_pray_id from t_assist_pray_item m \n" +
            "\tLEFT JOIN t_assist_pray y ON y.id =m.assist_pray_id \n" +
            "\tLEFT JOIN t_assist_pray_user r ON r.assist_pray_id =m.assist_pray_id \n" +
            " where m.item_id = #{itemId} and m.item_content_id = #{itemContentId}  and m.type =#{type}" +
            " and m.state = 0 and  m.distinction = #{distinction} AND r.uuid =#{uuid} AND y.state =1")
    List<Integer> findAssistPrayLessonItemById(@Param("itemId") Integer itemId,
                                               @Param("itemContentId") Integer itemContentId,
                                               @Param("type") Integer type,
                                               @Param("uuid") Integer uuid,
                                               @Param("distinction") Integer distinction);
    @DS("slave")
    @Select(" select assist_pray_id from t_assist_pray_item_user_count  " +
            "             where item_id = #{itemId} and type = #{type}  " +
            "             and item_content_id = #{itemContentId} " +
            "             and uuid=#{uuid} AND distinction = #{distinction} ")
    List<Integer>getAssistPrayIdByItemAndUuid(@Param("itemId") Integer itemId,
                                              @Param("type") Integer type,
                                              @Param("itemContentId") Integer itemContentId,
                                              @Param("uuid") Integer uuid,
                                              @Param("distinction") Integer distinction);


    /**
     * 修改助念 / 祈福 功课状态
     *
     * @param assistPrayItem
     */
    void updateAssistPrayItem(AssistPrayItem assistPrayItem);

    /**
     * 修改助念 / 祈福 功课状态
     *
     * @param assistPrayId
     * @param state
     */
    void updateStateByAssistPrayId(@Param("assistPrayId") Integer assistPrayId, @Param("state") int state);

    /**
     * 查询助念 / 祈福 群功课
     * @param id
     * @return
     */
    @DS("slave")
    List<AssistPrayItem> queryByAssistPrayIdJoinView(@Param("id") Integer id, @Param("uuid") Integer uuid);
    
    @DS("slave")
    @Select("SELECT COUNT(*) as count FROM t_assist_pray_item WHERE assist_pray_id=#{assistPray} AND total_done_num < IFNULL(target_done_num,0)")
   Integer findAssistPrayById(Integer assistPray);
}
