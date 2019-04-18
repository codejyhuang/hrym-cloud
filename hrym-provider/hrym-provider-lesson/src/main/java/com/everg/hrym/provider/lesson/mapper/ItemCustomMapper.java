package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.ItemCustom;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import org.apache.ibatis.annotations.Param;

public interface ItemCustomMapper {


    /**
     * 添加自定义功课
     * @param custom
     */
    void buildCustom(ItemCustom custom);

//    /**
//     * 查询用户 所有自定义功课
//     * @param qo
//     * @return
//     */
//    @DS("slave")
//    List<ItemCustom> listAllByUuid(QueryObjectParam qo);

    @DS("slave")
    ItemCustom findTaskPlanCustomById(@Param("uuid") Integer uuid, @Param("itemId") Integer itemId);
    
    /**
     * 自定义功课手动报数
     *
     * @param reportNum
     * @param upTime
     * @param taskId
     */
    
    void updateDoneNumByTaskId(@Param("reportNum") long reportNum,
                               @Param("num") long num,
                               @Param("upTime") Integer upTime,
                               @Param("taskId") Integer taskId);
    

    /**
     * 自定义功课更新《通用语句》
     *
     * @param itemCustom
     */
   
    void updateTaskPlanCustom(ItemCustom itemCustom);
    

    /**
     * 查询是否有同名自建功课
     * @param lessonName
     * @return
     */
    @DS("slave")
    int queryCountByCustomName(@Param("lessonName") String lessonName, @Param("userId") Integer userId);



    void updateCustomPrivacy(@Param("itemId") Integer itemId, @Param("privacy") Integer privacy);


    /**
     * 查询功课 创建人uuid
     * @param itemId
     * @return
     */
    int queryByCustomIdFromResourceCustom(@Param("itemId") Integer itemId, @Param("userId") Integer userId);
    
    /**
     * 保存功课记录
     */
     void saveTaskRecord(RecordCommon record);


    //////////////////合并账号 更新数据/////////////////
    void updateUuidOfCustomItem(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);

}
