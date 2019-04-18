package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.back.entity.FlockUserBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hrym13 on 2018/11/28.
 */
public interface FlockUserBackMapper {

    @DS("slave")
    FlockUserBack queryLastTimeSpecialBack(@Param("itemType") Integer itemType, @Param("itemId")Integer itemId, @Param("uuid")Integer uuid, @Param("contentId")Integer contentId);
    
    @DS("slave")
    List<FlockUserBack> queryDedicationVersesList(@Param("uuid") Integer uuid, @Param("flockId") Integer flockId);

    @DS("slave")
    FlockUserBack queryDedicationVersesById(Integer id);


    void updateSpecialBack(@Param("id") Integer id, @Param("info") String info, @Param("time") Integer time);



    @DS("slave")
    List<FlockUserBack> queryDedicationVerses(@Param("uuid") Integer uuid, @Param("flockId") Integer flockId,@Param("tableName") String tableName);

    void insertTestSpecialBack(FlockUserBack flockUserBack);

    @DS("slave")
    List<FlockUserBack> queryDedicationVersesRecordList(@Param("flockId") Integer flockId, @Param("ymd") String ymd, @Param("tableName") String tableName);
    
    void deleteSpecialBack(Integer id);

}
