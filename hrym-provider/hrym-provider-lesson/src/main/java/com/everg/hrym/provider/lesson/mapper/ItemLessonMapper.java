package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.ItemLessonTaskPlan;
import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemLessonMapper {




    /**
     * 修该 系统功课的 online_num
     * @param itemId
     * @param itemContentId
     */
    void updateOnlineNum(@Param("itemId") Integer itemId,
                         @Param("itemContentId") Integer itemContentId,
                         @Param("type") Integer type,
                         @Param("num") Integer num);
    

    List<RecordCommon> queryItemRecordList(Integer userId);




    @DS("slave")
    LessonCommon queryLessonFromView(@Param("itemId") Integer itemId, @Param("itemContentId") Integer itemContentId, @Param("type") Integer type);


    @DS("slave")
    List<LessonCommon> queryLessonListFromView(@Param("uuid")Integer uuid , @Param("type")Integer type, @Param("keywords")String keywords);
    
    /**
     * 功课报数
     *
     * @param reportNum
     * @param upTime
     * @param taskId
     */
   
    void updateDoneNumByTaskId(@Param("reportNum") long reportNum,
                               @Param("num") long num,
                               @Param("upTime") Integer upTime,
                               @Param("countMethod") Integer countMethod,
                               @Param("taskId") Integer taskId);
    
    
    /**
     * 根据任务id查询自定义任务
     *
     * @param
     * @return
     */
    @DS("slave")
    ItemLessonTaskPlan findTaskPlanLessonByTaskId(@Param("uuid") Integer uuid,
                                                  @Param("itemContentId") Integer itemContentId,
                                                  @Param("itemId")Integer itemId);
    
    /**
     * 功课更新《通用语句》
     *
     * @param taskPlanLesson
     */
    
    void updateTaskPlanLesson(ItemLessonTaskPlan taskPlanLesson);
    
    /**
     * 保存功课记录
     */
   void saveTaskRecord(RecordCommon record);
    
}
