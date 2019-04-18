package com.everg.hrym.provider.flock.service;

import com.alibaba.fastjson.JSONObject;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockRecordDO;
import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.api.user.facade.UserFacade;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.NumUtil;
import com.everg.hrym.provider.flock.mapper.FlockItemMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlockItemService {

    @Autowired
    private FlockItemMapper mapper;

    @Autowired
    private FlockRecordService recordService;

    @Autowired
    private FlockItemUserCountService countService;


    @Autowired
    private UserFacade userFacade;


    @Autowired
    private TaskFacade taskFacade;

    @Autowired
    private LessonFacade lessonFacade;

    /**
     * 获取上报记录动态数据
     *
     * @param currentPage
     * @param pageSize
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param timeType
     * @param uuid
     * @return
     */
    public Map<String, Object> flockMessageRecord(Integer currentPage, Integer pageSize,
                                                  Integer flockId, Integer itemId,
                                                  Integer itemContentId, Integer type,
                                                  Integer timeType, Integer uuid) {
        Map<String, Object> map = new HashMap<>();
        String tableName = "t_flock_record_" + DateUtil.getYear();
        PageHelper.startPage(currentPage, pageSize);
        List<FlockRecordDO> flockRecordDOList = new ArrayList<>();
        if (timeType.equals(0)) {
            flockRecordDOList = recordService.queryByFlockIdAndItemIdAndItemContentIdAndType(flockId, itemId, itemContentId, type, uuid);
            if (flockRecordDOList.size() != 0) {
                for (FlockRecordDO flockRecordDO : flockRecordDOList) {
                    flockRecordDO.setTimeStr(DateUtil.getTimeChaHour(
                            DateUtil.formatIntDate(flockRecordDO.getCreateTime()), new Date()));
                    flockRecordDO.setReportStr(NumUtil.getTotalNumStr(flockRecordDO.getNum(), "0"));
                }
            }
        } else if (timeType.equals(4)) {
            //查询累计
            flockRecordDOList = countService.queryByFlockIdAndItemIdAndItemContentIdAndType(flockId, itemId, itemContentId, type, uuid);
            Iterator<FlockRecordDO> iterator = flockRecordDOList.iterator();
            while (iterator.hasNext()) {
                FlockRecordDO recordDO = iterator.next();
                recordDO.setReportStr(NumUtil.getTotalNumStr(recordDO.getNum(), "0"));
            }
            FlockRecordDO userRecord = countService.queryByUuidFromItemCount(flockId, itemId, itemContentId, type, uuid);
            if (userRecord != null) {
                userRecord.setReportStr(NumUtil.getTotalNumStr(userRecord.getNum(), "0"));
            }
            map.put("userRecord", userRecord == null ? null : userRecord);
        } else {
            flockRecordDOList = recordService.queryByFlockIdAndItemIdAndItemContentIdAndTimeType(flockId,
                    itemId, itemContentId, type, timeType, tableName, DateUtil.getYMD(), DateUtil.getYear(), DateUtil.getMonth(), DateUtil.getTimesWeekmorning());
            Iterator<FlockRecordDO> iterator = flockRecordDOList.iterator();
            while (iterator.hasNext()) {
                FlockRecordDO recordDO = iterator.next();
                recordDO.setReportStr(NumUtil.getTotalNumStr(recordDO.getNum(), ""));
            }
            FlockRecordDO userRecord = recordService.queryByUuid(flockId,
                    itemId, itemContentId, type, tableName,
                    DateUtil.getYear(), DateUtil.getYMD(), DateUtil.getMonth(), timeType, uuid, DateUtil.getTimesWeekmorning());
            if (userRecord != null) {
                userRecord.setReportStr(NumUtil.getTotalNumStr(userRecord.getNum(), "0"));
            }
            map.put("userRecord", userRecord == null ? null : userRecord);

        }
        if (map.get("userRecord") == null) {
            //用户没有自己的上报记录时候
            UserAccount userAccount = userFacade.queryByUuid(uuid);
            FlockRecordDO recordDO = new FlockRecordDO();
            recordDO.setOrder(0);
            recordDO.setReportStr("0");
            recordDO.setAvatar(userAccount.getAvatar());
            recordDO.setNickName(userAccount.getNickname());
            map.put("userRecord", recordDO);
        }
        String unit = taskFacade.queryUnitByUuidAndItemIdAndItemContentIdAndType(uuid, itemId,
                itemContentId, type); //查询功课量词
        map.put("unit", unit == null ? "遍" : unit);
        map.put("hasNextPage", new PageInfo<>(flockRecordDOList).isHasNextPage());
        map.put("totalPage", new PageInfo<>(flockRecordDOList).getPages());
        map.put("total", new PageInfo<>(flockRecordDOList).getTotal());
        map.put("recordList", flockRecordDOList);
        return map;
    }


    /**
     * 查询共修群功课
     *
     * @param flockId
     * @return
     */
    public List<FlockItem> queryFlockItemList(Integer flockId) {
        return mapper.queryByFlockId(flockId);
    }

    public List<FlockItem> queryByFlockIdJoinView(Integer id, Integer uuid) {
        return mapper.queryByFlockIdJoinView(id, uuid);
    }

    public FlockItem queryByFlockIdAndItemIdAndContentIdAndType(Integer id, Integer itemId, Integer itemContentId, Integer type) {
        return mapper.queryByFlockIdAndItemIdAndContentIdAndType(id, itemId, itemContentId, type);
    }

    public void updateFlockItem(FlockItem flockItem) {
        mapper.updateFlockItem(flockItem);
    }


    public void insert(FlockItem flockItem) {
        mapper.insert(flockItem);
    }

    /**
     * 查找群功课
     *
     * @param flockId
     * @return
     */
    public List<FlockItem> queryByFlockId(Integer flockId) {
        return mapper.queryByFlockId(flockId);
    }

    public FlockItem selectFlockItemList(Integer itemContentId, String contentTable) {
        return mapper.selectFlockItemList(itemContentId, contentTable);
    }

    /**
     * 逻辑删除群功课
     *
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param state
     * @return
     */
    public int batchLogicRemoveFlockLesson(Integer flockId, Integer itemId, Integer itemContentId, Integer type, int state) {
        return mapper.batchLogicRemoveFlockLesson(flockId, itemId, itemContentId, type, state);
    }

    public List<Integer> getFlockIdByItemAndUuid(Integer itemId, Integer type, Integer itemContentId, Integer uuid) {

        List<Integer> list = mapper.getFlockIdByItemAndUuid(itemId, type, itemContentId, uuid);
        return list;
    }

    /**
     * 群功课备份
     */
    public Map<String, Object> exportFlockItem(Integer flockId) {
        Map<String, Object> map = new HashMap<>();

        List<FlockItem> flockItemName = mapper.queryFlockItemNameByFlockId(flockId);
        //功课
        List<Map<String, Object>> flockItems = mapper.queryFlockMessage(flockItemName, flockId);
//        //功课总统计
        Map<String, Object> itemToTal = mapper.queryFlockItemToTal(flockItemName, flockId);
//        System.out.println(itemToTal);
        //用户
        List<Map<String, Object>> userItemData = mapper.queryFlockItemUserCount(flockItemName, flockId);
        for (Map<String, Object> map1 : userItemData) {

            String timeStr = DateUtil.timestampToDates(DateUtil.currentSecond(), DateUtil.DATA_PATTON_YYYYMMDD);

            if (StringUtils.isNotBlank((String) map1.get("time"))) {

                map1.put("time", map1.get("time") + " - " + timeStr);
            } else {
                map1.put("time", timeStr);
            }

        }

        map.put("flockItems", flockItems);
        map.put("flockItemName", flockItemName);
        map.put("name", flockItemName.get(0).getName());
        map.put("userItemData", userItemData);
        map.put("itemToTal", itemToTal);

        return map;
    }


    public void updateStateByFlockId(Integer flockId, int state) {
        mapper.updateStateByFlockId(flockId, state);
    }

    // 群设置页面群回向和特别回向数量
    public Map<String, Object> flockMessageBack(Integer flockId, Integer uuid) {
        Map<String, Object> map = new HashMap<>();

        FlockRecordDO flockRe = mapper.getFlockDedicationNum(flockId, uuid);
        if (flockRe != null) {
            map.put("count", flockRe.getCount());
            map.put("flockCount", flockRe.getFlockCount());
        } else {
            map.put("count", 0);
            map.put("flockCount", 0);
        }
        return map;
    }


    public Map<String,Object> lessonList(Integer uuid,Integer currentPage, Integer pageSize, Integer type, String keywords, Integer flockId) {
        Map<String, Object> map = lessonFacade.listAll(uuid, currentPage, pageSize, type, keywords);
        Object obj = JSONObject.toJSON(map.get("list"));
        List<LessonCommon> list = JSONObject.parseArray(obj.toString(), LessonCommon.class);

        List<FlockItem> flockItems = queryFlockItemList(flockId);
        for (LessonCommon lesson : list) {
            for (FlockItem flockItem : flockItems) {
                if ((flockItem.getType().equals(lesson.getType())
                        && flockItem.getItemId().equals(lesson.getItemId())
                        && flockItem.getItemContentId().equals(lesson.getItemContentId()))
                ) {
                    lesson.setIsAdd(1);
                }
            }
        }
        map.put("list", list);
        return map;
    }
}
