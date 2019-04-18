package com.everg.hrym.provider.lesson.service;

import com.alibaba.fastjson.JSON;
import com.everg.hrym.api.back.entity.FlockUserBack;
import com.everg.hrym.api.back.entity.FlockUserBackJson;
import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.provider.lesson.mapper.FlockUserBackMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by hong on 2019/3/21.
 */
@Service
public class UserBackService {

    @Autowired
    private FlockUserBackMapper flockUserBackMapper;

    /**
     * 用户上一次特别回向文
     *
     * @param itemId
     * @return
     */

    public Map<String, Object> lastSpecialBack(Integer itemType, Integer itemId, Integer uuid, Integer contentId) {

        Map<String, Object> map = new HashMap<>();

        FlockUserBack flockUserBack = flockUserBackMapper.queryLastTimeSpecialBack(itemType, itemId, uuid, contentId);
        map.put("flockUserBack", flockUserBack);

        return map;
    }

    /**
     * 共修回向列表
     *
     * @param flockId
     * @return
     */
    public Map<String, Object> queryDedicationVersesList(Integer uuid, Integer type,
                                                         Integer currentPage, Integer pageSize,
                                                         Integer flockId,
                                                         Integer year) {

        String url = "https://upd.everglamming.com:8089/group1/M00/00/13/wKgAHFoBUH-ARZFiAAC0d7SdfuU124.jpg";
        List list = new ArrayList();
        List<FlockUserBack> flockUserBackList = null;

        // 0:群回向\n1:特别回向
        if (type == 0) {

            PageHelper.startPage(currentPage, pageSize);
            flockUserBackList = flockUserBackMapper.queryDedicationVersesList(uuid, flockId);

            if (flockUserBackList != null) {
                for (FlockUserBack userBack : flockUserBackList) {

                    Map<String, Object> map = new HashMap<>();

//
                    FlockUserBackJson json = JSON.parseObject(userBack.getDescJson(), FlockUserBackJson.class);

                    System.out.println(json);
                    map.put("ymd", userBack.getYmd());
                    map.put("name", json.getName());
                    map.put("nickName", json.getNickname());
                    map.put("avatarUrl", json.getAvatarUrl());
                    //recordId群回向时为群ID
                    map.put("flockId", userBack.getRecordId());
                    map.put("info", userBack.getInfo());
                    map.put("id", userBack.getId());

                    String time = DateUtil.timestampToDates(DateUtil.currentSecond(), DateUtil.TIME_PATTON_DEFAULT1);

                    if (userBack.getTime() != null) {
                        time = DateUtil.timestampToDates(userBack.getTime(), DateUtil.TIME_PATTON_DEFAULT1);
                    }
                    map.put("time", time);

                    map.put("viewStr", (json.getNickname() == null ? "师兄" : json.getNickname()) + "." + time);
                    list.add(map);
                }

            }

        } else {

            String info = "愿以此功德,庄严佛净土。上报四重恩,下济三途苦。若有见闻者,悉发菩提心。尽此一报身,同生极乐国。";
            PageHelper.startPage(currentPage, pageSize);
            String tableName = "t_flock_record_" + DateUtil.getYear();
            if (year != null && year > 2017) {
                tableName = "t_flock_record_" + year;
            }
            flockUserBackList = flockUserBackMapper.queryDedicationVerses(uuid, flockId, tableName);

            if (flockUserBackList != null) {

                for (FlockUserBack userBack : flockUserBackList) {

                    Map<String, Object> map = new HashMap<>();

                    map.put("info", userBack.getInfo() == null ? info : userBack.getInfo());
                    map.put("id", userBack.getId());
                    map.put("itemId", userBack.getItemId());
                    map.put("contentId", userBack.getContentId());
                    map.put("itemType", userBack.getItemType());
                    map.put("viewStr", DateUtil.getTimeChaHour(DateUtil.formatIntDate(userBack.getTime() == null ? DateUtil.currentSecond() : userBack.getTime()), new Date()));
                    if (StringUtils.isNotBlank(userBack.getVersionName())) {
                        map.put("versionName", userBack.getVersionName());
                    } else {
                        map.put("versionName", "功课");
                    }
                    list.add(map);
                }
            }
        }

        PageInfo pageInfo = new PageInfo(flockUserBackList);

        long total = pageInfo.getTotal();
        long totalPages = pageInfo.getPages();

        Map<String, Object> maps = new HashMap<>();
        maps.put("list", list);
        maps.put("total", total);
        maps.put("totalPages", totalPages);
        return maps;
    }

    /**
     * 我的特别回向详情
     *
     * @param id
     * @return
     */
    public Map<String, Object> querySpecialBackDetails(Integer id) {

        Map<String, Object> map = new HashMap<>();
        String info = "愿以此功德,庄严佛净土。上报四重恩,下济三途苦。若有见闻者,悉发菩提心。尽此一报身,同生极乐国。";

        String times = DateUtil.timestampToDates(DateUtil.currentSecond(), DateUtil.DATA_PATTON_YYYYMMDD2);

        FlockUserBack flockUserBack = flockUserBackMapper.queryDedicationVersesById(id);

        if (flockUserBack != null) {

            if (flockUserBack.getTime() != null) {

                times = DateUtil.timestampToDates(flockUserBack.getTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
            }
            String weeks = DateUtil.dateToWeek(times, DateUtil.DATA_PATTON_YYYYMMDD2);

            map.put("timeStr", times);
            map.put("weeks", weeks);
            map.put("info", flockUserBack.getInfo() == null ? info : flockUserBack.getInfo());
            map.put("id", flockUserBack.getId());
            map.put("flag", flockUserBack.getFlag());
        }
        return map;
    }


    /**
     * 编辑特别回向文
     *
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSpecialBack(Integer id, String info) {
        Integer time = DateUtil.currentSecond();
        flockUserBackMapper.updateSpecialBack(id, info, time);
    }


    /**
     * 群回向详情dejson,nameJson
     *
     * @param id
     * @return
     */
    public Map<String, Object> queryDedicationVerses(Integer id) {
        Map<String, Object> map = new HashMap<>();
        FlockUserBack flockUserBack = flockUserBackMapper.queryDedicationVersesById(id);
        if (flockUserBack != null) {
            FlockUserBackJson descJson = JSON.parseObject(flockUserBack.getDescJson(), FlockUserBackJson.class);
            List<LessonCommon> flockItemList = JSON.parseArray(flockUserBack.getItemJson(), LessonCommon.class);
            map.put("descJson", descJson);
            map.put("itemJson", flockItemList);
        }
        return map;
    }

    /**
     * 群回向详情页今日群回向记录
     *
     * @param flockId
     * @return
     */
    public Map<String, Object> queryDedicationVersesRecordList(String timeStr,
                                                               Integer currentPage, Integer pageSize,
                                                               Integer flockId, String ymd) {
        Map<String, Object> map = new HashMap<>();
        String tableName = null;
        if (timeStr != null) {
            tableName = "t_flock_record_" + DateUtil.getYear(timeStr, DateUtil.TIME_PATTON_DEFAULT1);
        } else {
            tableName = "t_flock_record_view";
        }
        PageHelper.startPage(currentPage, pageSize);
        List<FlockUserBack> backList = flockUserBackMapper.queryDedicationVersesRecordList(flockId, ymd, tableName);
        PageInfo pageInfo = new PageInfo(backList);
        long total = pageInfo.getTotal();
        long totalPages = pageInfo.getPages();
        map.put("list", backList);
        map.put("total", total);
        map.put("totalPages", totalPages);
        return map;
    }


}
