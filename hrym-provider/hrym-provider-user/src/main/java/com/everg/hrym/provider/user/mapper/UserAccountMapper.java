package com.everg.hrym.provider.user.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.user.entity.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserAccountMapper {

    int insert(UserAccount record);

    @DS("slave")
    UserAccount selectByPrimaryKey(Integer uuid);

    @DS("slave")
    List<UserAccount> selectAll();

    int updateByPrimaryKey(UserAccount record);

    @DS("slave")
    List<UserAccount> findUserByUnionIdOpenId(@Param("unionId") String unionId, @Param("openId") String openId);

    void deleteUserByUuid(@Param("uuid") Integer uuid2);

    void updateUserAccountNameByUuid(@Param("u") Map<String, Object> map,@Param("updatedTime") Integer updatedTime, @Param("uuid") Integer uuid);



    @DS("slave")
    Integer findUuidPhoneByUuid(Integer uuid);
    
    void insertUuidPhone(@Param("flag") Integer flag,@Param("phone") String  phone, @Param("uuid") Integer uuid);
    
    void updateUuidPhoneByUuid(@Param("uuid") Integer uuid, @Param("phone") String phone);



}
