package com.everg.hrym.provider.flock.service;

import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import com.everg.hrym.common.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by hrym13 on 2019/3/27.
 */
@Service
public class FlockReportCountService {

    @Autowired
    private FlockUserService flockUserService;
    @Autowired
    private FlockItemService flockItemService;
    @Autowired
    private FlockItemUserCountService flockItemUserCountService;
    @Autowired
    private FlockRecordService flockRecordService;

    /**
     * 共修报数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> flockCountReport(List<Integer> flockIds, Integer uuid,
                                                Integer recordId, Integer itemId,
                                                Integer itemContentId, Integer type,
                                                Float lat, Float lon, Double num) {

        Map<String, Object> map = new HashMap<>();
        List<FlockRecordDO> list = new ArrayList<>();

        String tableName = "t_flock_record_" + DateUtil.getYear();
        int isDelete = 0;
        //查询这个功课对应的所有群
        List<Integer> flock2Report = new ArrayList();

        if (flockIds.size() == 0) {
            map.put("isDelete", isDelete);
            map.put("recordId", recordId);
            return map;
        }
        flockIds = new ArrayList<>(new HashSet<>(flockIds));

        Iterator<Integer> listIt = flockIds.iterator();
        while (listIt.hasNext()) {
            // 判断是否在群
            FlockItem flockUser = flockUserService.queryByFlockIdAndUuid(listIt.next(), uuid);
            if (flockUser == null) {
                isDelete = 1;
                map.put("isDelete", isDelete);
                return map;
            }
        }
        for (Integer flockId : flockIds) {
            //在群里上报
            flock2Report.add(flockId);

        }


        //查询这个人的所有统计表信息
        List<Integer> flock2Count = flockItemService.getFlockIdByItemAndUuid(itemId
                , type
                , itemContentId
                , uuid);

        for (Integer flockId : flock2Report) {

            //整合需要添加的record
            FlockRecordDO record = new FlockRecordDO();
            record.setCreateTime(DateUtil.currentSecond());
            record.setItemContentId(itemContentId == null ? 0 : itemContentId);
            record.setItemId(itemId == null ? 0 : itemId);
            record.setLat(lat);
            record.setLon(lon);
            record.setNum(num);
            record.setUuid(uuid);
            record.setParentId(recordId);
            record.setType(type);
            record.setFlockId(flockId);

            //遍历flock2Report,找出需要插入count的flock_id
            if (!flock2Count.contains(flockId)) {

                flockItemUserCountService.insertFlockItemUser(record);
            }

            list.add(record);
        }

        if (list.size() != 0) {
            //插入上报明细表
            flockRecordService.insertFlockRecord(list, tableName);
        }

        map.put("isDelete", isDelete);
        map.put("recordId", recordId);

        return map;
    }


}
