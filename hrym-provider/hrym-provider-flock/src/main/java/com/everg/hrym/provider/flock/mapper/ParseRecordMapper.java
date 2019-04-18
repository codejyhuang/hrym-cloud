package com.everg.hrym.provider.flock.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.flock.entity.ParseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hrym13 on 2018/11/9.
 */
@Repository
public interface ParseRecordMapper {




    /**
     *
     * @param relationId
     * @param from
     * @return
     */
    @DS("slave")
    ParseRecord queryByRelationIdAndRelationTypeAndFrom(@Param("relationId") Integer relationId,
                                                        @Param("from") Integer from);

    /**
     * 保存点赞数据
     * @param ps
     */
    void insert(ParseRecord ps);


    /**
     *删除点赞数据
     * @param relationId
     * @param from
     * @param programType
     * @return
     */
    int cancelByRelationIdAndUuid(@Param("relationId") Integer relationId,
                                  @Param("from") Integer from, @Param("recordType") Integer programType);

    /**
     *
     * @param relationId
     * @param from
     * @param recordType
     * @return
     */
    @DS("slave")
    ParseRecord queryByRelationIdAndRecordTypeAndFrom(@Param("relationId") Integer relationId,
                                                      @Param("from") Integer from,
                                                      @Param("recordType") Integer recordType);


    @DS("slave")
    List<ParseRecord> queryLikeMember(@Param("relationId") Integer relationId,@Param("recordType") int programType);

}
