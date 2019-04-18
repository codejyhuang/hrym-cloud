package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.ItemUserUnit;
import org.apache.ibatis.annotations.Param;

public interface ItemUserUnitMapper {

    /**
     * 查询用户功课量词
     * @param uUid
     * @param itemId
     * @param itemContentId
     * @param type
     * @return
     */
    @DS("slave")
    String queryUnitByUuidAndItemIdAndItemContentIdAndType(@Param("uuid") Integer uUid,
                                                           @Param("itemId") Integer itemId,
                                                           @Param("itemContentId") Integer itemContentId,
                                                           @Param("type") Integer type);

    /**
     * 添加用户 功课 量词 信息
     * @param itemUserUnit
     */
    void insert(ItemUserUnit itemUserUnit);
    
    @DS("slave")
    ItemUserUnit queryItemUserUnit(@Param("uuid") Integer uUid,
                                   @Param("itemId") Integer itemId,
                                   @Param("itemContentId") Integer itemContentId,
                                   @Param("type") Integer type);
    
    void updateItemUserUnitById(ItemUserUnit itemUserUnit);
}
