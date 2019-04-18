package com.everg.hrym.provider.user.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.user.entity.WxUserAccount;

import java.util.List;

public interface WxUserAccountMapper {

    int insert(WxUserAccount record);

    @DS("slave")
    WxUserAccount selectByPrimaryKey(Integer id);

    @DS("slave")
    List<WxUserAccount> selectAll();

    int updateByPrimaryKey(WxUserAccount record);
}