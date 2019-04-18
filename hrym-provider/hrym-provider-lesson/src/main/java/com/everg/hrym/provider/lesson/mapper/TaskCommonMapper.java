package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.TaskCommon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hrym13 on 2018/4/14.
 */
@Repository
public interface TaskCommonMapper {


    /**
     * 根据功课 去查询 任务信息
     *
     * @param type
     * @param itemId
     * @param itemContentId
     * @param uUid
     * @param tableName
     * @param isExist
     * @return
     */
    @DS("slave")
    int queryCountByItemIdAndItemContentIdAndTableNameAndUuid(@Param("type") Integer type,
                                                              @Param("itemId") Integer itemId,
                                                              @Param("itemContentId") Integer itemContentId,
                                                              @Param("uuid") Integer uUid,
                                                              @Param("tableName") String tableName,
                                                              @Param("isExist") Integer isExist);

    /**
     * 添加  task 记录
     *
     * @param taskCommon
     * @param tableName
     */
    void insert(@Param("task") TaskCommon taskCommon, @Param("tableName") String tableName, @Param("type") Integer type);

    /**
     * 更新功课的任务
     *
     * @param type
     * @param itemId
     * @param itemContentId
     * @param uUid
     * @param tableName
     */
    void updateBookIsExist(@Param("type") Integer type,
                           @Param("itemId") Integer itemId,
                           @Param("itemContentId") Integer itemContentId,
                           @Param("uuid") Integer uUid,
                           @Param("tableName") String tableName);

    /**
     * 更新功课的任务
     *
     * @param type
     * @param itemId
     * @param itemContentId
     * @param uUid
     * @param tableName
     */
    void updateIsExist(@Param("type") Integer type,
                       @Param("itemId") Integer itemId,
                       @Param("itemContentId") Integer itemContentId,
                       @Param("uuid") Integer uUid,
                       @Param("tableName") String tableName);


    /**
     * 更新任务 doneNum
     *
     * @param taskId
     * @param num
     * @param tableName
     * @param type
     */
    void updateDoneNumByTaskId(@Param("taskId") Integer taskId,
                               @Param("num") Integer num,
                               @Param("tableName") String tableName,
                               @Param("type") Integer type,
                               @Param("isRemove") Integer isRemove);


    /**
     * 我的功课  查询系统  自建功课 task
     *
     * @param userId
     * @return
     */
    @DS("slave")
    List<TaskCommon> queryTaskByUuidFromView(@Param("userId") Integer userId,
                                             @Param("taskId") Integer taskId);

    /**
     * 主键更新  任务 task 的状态
     * @param taskId
     * @param state
     */
    void updateStateByPrimaryKey(@Param("taskId") Integer taskId,
                                 @Param("state") int state,
                                 @Param("type") Integer type,
                                 @Param("tableName") String tableName);

    /**
     * 根据taskId 查询任务
     * @param tableName
     * @return
     */
    @DS("slave")
    TaskCommon queryTaskFromTable(@Param("taskId") Integer taskId, @Param("tableName") String tableName, @Param("type") Integer type);

    @DS("slave")
    TaskCommon totalUserBackup(Integer userId);

    @DS("slave")
    List<TaskCommon> queryItemListDeatil(Integer userId);


    /**
     * 查询  bookcase  经书 任务
     *
     * @param itemId
     * @param uuid
     * @return
     */
    @DS("slave")
    TaskCommon findBookTaskPlan(@Param("itemId") Integer itemId, @Param("uuid") Integer uuid);

    ////////////////////////////////////////////////////////  导入数据////////////////////////////////////////////////////////////////////////


    void updateBookcaseReport(@Param("num") Integer num,
                              @Param("percent") String percent,
                              @Param("taskId") Integer taskId,
                              @Param("time") Integer time);


    void updateCustomReport(@Param("reportNum") long reportNum,
                            @Param("upTime") Integer upTime,
                            @Param("taskId") Integer taskId);


    void updateLessonReport(@Param("reportNum") long reportNum,
                            @Param("upTime") Integer upTime,
                            @Param("taskId") Integer taskId);




    void updateTaskOrder(@Param("taskId") Integer taskId,
                         @Param("order") Integer order,
                         @Param("type") Integer type,
                         @Param("tableName") String tableName);


    Integer queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(@Param("itemId") Integer itemId, @Param("itemContentId") Integer itemContentId,
                                                              @Param("type") Integer type,@Param("uuid") Integer uuid);


    ////////////////////////////////////////////////////////////////////////合并账号///////////////////////////////////////////////////

    void updateUuidOfTask(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);

}
