package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import org.apache.ibatis.annotations.Param;

public interface BookMapper {
    
    
    @DS("slave")
    RecordCommon findFlockRecordBookByUuid(@Param("itemId") Integer itemId, @Param("uuid") Integer uuid);
    
    /**
     * 更新经书上报数据
     *
     * @param num
     * @param indexId
     */
    void updateReport(@Param("num") Integer num, @Param("percent") String percent, @Param("indexId") Integer indexId, @Param("time") Integer time);
    
    
    void saveRecord(RecordCommon record);
}
