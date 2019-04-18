package com.everg.hrym.provider.flock.service;

import com.everg.hrym.api.flock.entity.FlockItemUserCount;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import com.everg.hrym.provider.flock.mapper.FlockItemUserCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class FlockItemUserCountService {

    @Autowired
    private FlockItemUserCountMapper mapper;

    public void insert(FlockItemUserCount flockItemUserCount) {
        mapper.save(flockItemUserCount);
    }

    public void insertFlockItemUser(FlockRecordDO recordDO) {
        mapper.insertFlockItemUser(recordDO);
    }

    public List<FlockRecordDO> queryByFlockIdAndItemIdAndItemContentIdAndType(Integer flockId, Integer itemId, Integer itemContentId, Integer type, Integer userId) {
        return mapper.queryByFlockIdAndItemIdAndItemContentIdAndType(flockId, itemId, itemContentId, type, userId);
    }

    public FlockRecordDO queryByUuidFromItemCount(Integer flockId, Integer itemId, Integer itemContentId, Integer type, Integer userId) {
        return mapper.queryByUuidFromItemCount(flockId, itemId, itemContentId, type, userId);
    }

    public FlockItemUserCount queryByFlockIdAndItemIdAndItemContentIdAndTypeAndUuid(Integer flockId, Integer itemId, Integer itemContentId, Integer type, Integer uuid) {
        return mapper.queryByFlockIdAndItemIdAndItemContentIdAndTypeAndUuid(flockId, itemId, itemContentId, type, uuid);
    }

    public void updateUuidOfFlockItemCount(Integer uuid, Integer oldUuid) {
        mapper.updateUuidOfFlockItemCount(uuid,oldUuid);
    }
}
