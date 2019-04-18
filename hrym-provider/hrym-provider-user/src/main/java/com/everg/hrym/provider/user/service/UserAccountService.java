package com.everg.hrym.provider.user.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.flock.facade.FlockFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.provider.user.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserAccountService {

     @Autowired
     private UserAccountMapper mapper;

     @Autowired
     private TaskFacade taskFacade;

     @Autowired
     private FlockFacade flockFacade;



     public UserAccount queryByUuid(Integer uuid) {
          return mapper.selectByPrimaryKey(uuid);
     }

     @Transactional
     @LcnTransaction
     public void mergeAccount(Integer uuid, Integer oldUuid) {
          taskFacade.mergeAccount(uuid, oldUuid);
          flockFacade.mergeAccount(uuid, oldUuid);
          mapper.deleteUserByUuid(oldUuid);
     }


     public List<UserAccount> findUserByUnionIdOpenId(String unionId, String openId) {
          return mapper.findUserByUnionIdOpenId(unionId, openId);
     }








     public void updateUserAccountNameByUuid(Map<String, Object> map, Integer updatedTime, Integer uuid) {
          mapper.updateUserAccountNameByUuid(map, updatedTime, uuid);
     }


     public void insert(UserAccount userAccount) {
          mapper.insert(userAccount);
     }

     public Integer findUuidPhoneByUuid(Integer uuid) {
          return mapper.findUuidPhoneByUuid(uuid);
     }

     public void insertUuidPhone(Integer flag, Integer uuid, String phone) {
          mapper.insertUuidPhone(flag, phone, uuid);
     }

     public void updateUuidPhoneByUuid(Integer uuid, String phone) {
          mapper.updateUuidPhoneByUuid(uuid, phone);
     }


}
