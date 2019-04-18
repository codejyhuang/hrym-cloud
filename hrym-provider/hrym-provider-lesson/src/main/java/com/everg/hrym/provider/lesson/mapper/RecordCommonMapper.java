package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hrym13 on 2018/4/14.
 */
@Repository
public interface RecordCommonMapper {


    /**
     * 删除app 中的 record 记录
     *
     * @param parentId
     */
    void removeByPrimaryKey(@Param("parentId") Integer parentId, @Param("tableName") String tableName);


    /**
     * 查询taskRecord  今日报数量
     *
     * @param taskId
     * @param timeType
     * @param format
     * @return
     */
    @DS("slave")
    Long queryByUuidAndItemIdAndItemIdAndTypeAndTableName(@Param("taskId") Integer taskId,
                                                          @Param("timeType") Integer timeType,
                                                          @Param("format") Integer format,
                                                          @Param("tableName") String tableName);


    /**
     * 查询 record   from  t_task_record_lesson
     *
     * @param taskId
     * @return
     */
    @DS("slave")
    List<RecordCommon> queryRecordByTaskId(@Param("taskId") Integer taskId,
                                           @Param("tableName") String tableName);



    //////////////////////////////////////////////////////////////////////////////////

    Integer queryReportCount(@Param("tableName") String tableName, @Param("userId") Integer userId, @Param("taskId") Integer taskId);


    @DS("slave")
    Integer queryBookHistoryRecord(Integer taskId);

    void insertBookRecord(RecordCommon record);

    void updateBookHistoryRecordNum(@Param("num") int num, @Param("taskId") Integer taskId);

    @DS("slave")
    Long queryCustomHistoryRecordNum(Integer taskId);
    //导入历史记录
    void saveCustomRecord(RecordCommon custom);

    void updateCustomHistoryRecordNum(@Param("reportNum") long reportNum, @Param("taskId") Integer taskId);

    @DS("slave")
    Long queryLessonHistoryRecordNum(Integer taskId);

    void saveLessonRecord(RecordCommon lesson);

    void updateLessonHistoryRecordNum(@Param("reportNum") long reportNum, @Param("taskId") Integer taskId);

    /////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////合并账号 更新数据////////////////////////////////////
    void updateUuidOfRecord(@Param("uuid")Integer uuid,@Param("oldUuid") Integer oldUuid);

}
