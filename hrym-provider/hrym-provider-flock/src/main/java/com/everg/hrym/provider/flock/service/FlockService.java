package com.everg.hrym.provider.flock.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.flock.entity.*;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.NumUtil;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.flock.exception.FlockResultCode;
import com.everg.hrym.provider.flock.mapper.FlockMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.everg.hrym.common.core.util.DateUtil.timestampToDates;

/**
 * Created by hong on 2019/3/21.
 */
@Service
public class FlockService {

    @Autowired
    private FlockMapper mapper;

    @Autowired
    private FlockItemService flockItemService;

    @Autowired
    private FlockRecordService flockRecordService;

    @Autowired
    private FlockUserService flockUserService;

    @Autowired
    private FlockItemUserCountService flockItemUserCountService;

    @Autowired
    private FlockReportCountService flockReportCountService;


    @Autowired
    private TaskFacade taskFacade;

    @Autowired
    private LessonFacade lessonFacade;


    /**
     * 创建共修群
     *
     * @param voList
     * @param uuid
     * @param flockName
     * @param dedicationVerses
     * @param intro
     * @param privacy
     * @param intro
     * @return
     */
    @Transactional
    @LcnTransaction
    public Map<String, Object> createFlock(List<ItemVO> voList, Integer uuid, String flockName,
                                           String dedicationVerses,
                                           Integer privacy, String intro
    ) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isNotBlank(flockName)) {
            map.put("state", 1);
            map.put("errorMsg", "请输入正确的群名称");
            return map;
        }

        Integer flockCount = mapper.queryCountByName(flockName);
        if (flockCount != null && flockCount > 0) {
            map.put("state", 2);
            map.put("errorMsg", "该群名已经存在,请重新输入群名称");
            return map;
        }


        UserAccount userAccount = new UserAccount();
        userAccount.setUuid(uuid);
        //创建共修群
        Flock flock = new Flock();
        flock.setCreateUser(userAccount);
        flock.setCreateTime(DateUtil.currentSecond());
        flock.setDayDoneNum(new Double(0));
        flock.setTotalDoneNum(new Double(0));
        flock.setDedicationVerses(dedicationVerses);
        flock.setName(flockName);
        flock.setPrivacy(privacy == null ? Flock.DEFAULT_PRIVACY_STATE : privacy);
        flock.setItemJoinNum(1);
        flock.setIntro(intro);
        flock.setItemNum(voList.size());
        mapper.createFlock(flock);
        //维护群功课关系
        for (ItemVO itemVO : voList) {

            String unit;
            itemVO.setCount(1);
            FlockItem flockItem = new FlockItem();
            flockItem.setCreateId(-1);
            flockItem.setDayDoneNum(new Double(0));
            flockItem.setTotalDoneNum(new Double(0));
            flockItem.setFlockId(flock.getId());
            flockItem.setItemId(itemVO.getItemId());
            flockItem.setOrder(0);
            flockItem.setState(0);
            flockItem.setType(itemVO.getType());
            flockItem.setItemContentId(itemVO.getItemContentId() == null ? 0 : itemVO.getItemContentId());
            flockItem.setCreateId(itemVO.getUuid() == null ? 0 : itemVO.getUuid());
            flockItem.setUnit("遍");
            flockItemService.insert(flockItem);


            //维护  功课用户的关系  t_flock_item_user_count
            FlockItemUserCount flockItemUserCount = new FlockItemUserCount();
            flockItemUserCount.setItemId(itemVO.getItemId());
            flockItemUserCount.setFlockId(flock.getId());
            flockItemUserCount.setItemContentId(itemVO.getItemContentId());
            flockItemUserCount.setUuid(uuid);
            flockItemUserCount.setTotal(new Double(0));
            flockItemUserCount.setType(itemVO.getType());
            flockItemUserCountService.insert(flockItemUserCount);

        }
        //    维护 群 用户表
        FlockUser flockUser = new FlockUser();
        flockUser.setFlockId(flock.getId());
        flockUser.setType(FlockUser.ROLE_ADMIN);
        flockUser.setUuid(uuid);
        flockUser.setOrder(0);
        flockUser.setCreateTime(DateUtil.currentSecond());
        flockUserService.insert(flockUser);
        Integer flockId = flock.getId();
        map.put("flockId", flockId);
        map.put("state", 0);

        //更新功课人数
        lessonFacade.updateOnlineNum(voList);

        //维护APP 任务task
        taskFacade.preserveTask(voList, uuid);

        return map;
    }

    /**
     * 用户的所有群
     *
     * @param uuid
     * @return
     */
    public Map<String, Object> listFlockByUuid(Integer uuid) {
        Map<String, Object> map = new HashMap<>();
        List<Flock> flockList = mapper.listByIds(uuid);
        if (flockList.size() != 0) {
            for (Flock flock : flockList) {
                if (flock == null) {
                    continue;
                }
                StringBuilder sb = new StringBuilder(80);
                if (flock.getCreateUser() == null) {
                    UserAccount wechatUserAccount = new UserAccount();
                    wechatUserAccount.setNickname("师兄");
                    wechatUserAccount.setAvatar("https://upd.everglamming.com:8089/group1/M00/00/13/wKgAHFoBUH-ARZFiAAC0d7SdfuU124.jpg");
                    flock.setCreateUser(wechatUserAccount);
                }

                flock.setViewStr(flock.getCreateUser().getNickname() == null ? "师兄" : flock.getCreateUser().getNickname() +
                        "创建于 · " +
                        DateUtil.getTimeChaHour(DateUtil.formatIntDate(flock.getCreateTime()), new Date()));
                if (flock.getAvatarUrl() != null) {
                    flock.getCreateUser().setAvatar(flock.getAvatarUrl());
                }

                List<FlockItem> flockItemList = flock.getFlockItems();
                if (flockItemList.size() != 0) {
                    for (FlockItem flockItem : flockItemList) {
                        sb.append("《");
                        if (flockItem != null) {
                            sb.append(flockItem.getLessonName() + "》");
                        }
                    }
                }
                flock.setLessonStr(sb.toString());
            }
        }
        map.put("flockList", flockList);
        return map;
    }


    /**
     * 群详情
     *
     * @param flockId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Map<String, Object> selectById(Integer flockId, Integer uuid,
                                          Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        //查询群信息
        Flock flock = mapper.queryByPrimaryKey(flockId);
        //设置报数显示str
        flock.setDayNumStr(NumUtil.getTotalNumStr(flock.getDayDoneNum(), "0"));
        flock.setTotalNumStr(NumUtil.getTotalNumStr(flock.getTotalDoneNum(), "0"));
        UserAccount userAccount = flock.getCreateUser();
        String timeStr = timestampToDates(flock.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2);

        //所有群功课
        List<FlockItem> lessonDOList = flockItemService.queryByFlockIdJoinView(flock.getId(), uuid);
        PageHelper.startPage(currentPage, 20);
        List<FlockRecordDO> flockRecordDOList = flockRecordService.queryByFlockId(flock.getId(), lessonDOList, uuid, "t_flock_record_" + DateUtil.getYear(), "t_flock_record_" + (DateUtil.getYear() - 1));
//        //遍历所有功课
        for (FlockRecordDO flockRecordDO : flockRecordDOList) {
            flockRecordDO.setReportStr(NumUtil.getTotalNumStr(flockRecordDO.getNum(), "0") + (StringUtils.isNotBlank(flockRecordDO.getUnit()) ? flockRecordDO.getUnit() : "遍"));
            //遍历所有 群功课
            for (FlockItem lessonDO : lessonDOList) {
                if ((lessonDO.getType().equals(flockRecordDO.getType())
                        && lessonDO.getItemContentId().equals(flockRecordDO.getItemContentId())
                        && lessonDO.getItemId().equals(flockRecordDO.getItemId()))
                ) {
                    flockRecordDO.setLessonName(lessonDO.getLessonName());
                    flockRecordDO.setTimeStr(DateUtil.getTimeChaHour(
                            DateUtil.formatIntDate(flockRecordDO.getCreateTime()), new Date()));
                }
            }

        }
        try {
            map.put("timeStr", DateUtil.daysBetween(flock.getCreateTime(), DateUtil.currentSecond()) + "天");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageInfo<FlockRecordDO> pageInfo = new PageInfo<>(flockRecordDOList);
        List<Integer> userIds = flockUserService.queryUuidListByFlockId(flockId);
        map.put("flock", flock);
        map.put("createTimeStr", timeStr + "   " + DateUtil.dateToWeek(timeStr, DateUtil.DATA_PATTON_YYYYMMDD2));
        map.put("isJoin", userIds.contains(uuid) ? 1 : 0);
        map.put("isAdmin", uuid.equals(userAccount.getUuid()) ? 1 : 0);
        map.put("flockLessonList", lessonDOList);
        map.put("recordList", flockRecordDOList);
        map.put("totalPage", pageInfo.getPages());
        map.put("recordTotal", pageInfo.getTotal());
        map.put("hasNextPage", pageInfo.isHasNextPage());
        return map;
    }

    /**
     * 添加功课
     *
     * @param voList  功课集合
     * @param flockId 群id
     */
    @Transactional
    @LcnTransaction
    public void addItem(List<ItemVO> voList, Integer flockId, Integer uuid) {
        if (voList.size() != 0) {

            FlockItem flockItem = flockItemService.queryByFlockIdAndItemIdAndContentIdAndType(flockId, voList.get(0).getItemId(), voList.get(0).getItemContentId(), voList.get(0).getType());
            if (flockItem != null && flockItem.getState() == 0) {
                throw new HrymException(FlockResultCode.ERROR4);
            }
            //更新群功课数量
            mapper.updateFlockItemNum(flockId, voList.size());
            //查询群用户数量
            Integer count = flockUserService.queryCountByFlockId(flockId);
            //维护群功课关系
            for (ItemVO itemVO : voList) {
                if (flockItem != null && flockItem.getState() == 1) {
                    flockItem.setState(0);
                    flockItemService.updateFlockItem(flockItem);
                } else if (flockItem == null) {
                    flockItem = new FlockItem();
                    flockItem.setCreateId(itemVO.getUuid());
                    flockItem.setDayDoneNum(new Double(0));
                    flockItem.setTotalDoneNum(new Double(0));
                    flockItem.setFlockId(flockId);
                    flockItem.setItemId(itemVO.getItemId());
                    flockItem.setOrder(0);
                    flockItem.setType(itemVO.getType());
                    //系统功课
                    flockItem.setItemContentId(itemVO.getItemContentId() == null ? 0 : itemVO.getItemContentId());

                    flockItemService.insert(flockItem);
                }
                itemVO.setCount(count);
            }

            lessonFacade.updateOnlineNum(voList);

            taskFacade.preserveTask(voList, uuid);
        }
    }


    /**
     * 批量删除功课
     *
     * @param voList
     * @param flockId
     */
    @Transactional
    @LcnTransaction
    public void removeFlockLesson(List<ItemVO> voList, Integer flockId) {
        if (voList.size() != 0) {
            Flock flock = mapper.queryByPrimaryKey(flockId);
            if (flock.getItemNum() <= voList.size()) {
                throw new HrymException(FlockResultCode.ERROR3);
            }
            //查询群用户数量
            Integer count = flockUserService.queryCountByFlockId(flockId);
            for (ItemVO itemVO : voList) {
                int rows = flockItemService.batchLogicRemoveFlockLesson(flockId, itemVO.getItemId(), itemVO.getItemContentId(), itemVO.getType(), 1);
                itemVO.setCount(rows);
            }
            mapper.updateFlockItemNum(flockId, -voList.size());

            lessonFacade.updateOnlineNum(voList);
        }
    }


    /**
     * 是否加群
     *
     * @param flockId
     * @return
     */
    public Map<String, Object> invitePage(Integer uuid, Integer flockId) {
        Map<String, Object> map = new HashMap<>();
        if (uuid != null && flockId != null) {
            FlockUser flockUser = mapper.listflockUserById(uuid, flockId);
            //判断是否加群：《1：已加群》
            if (flockUser != null) {
                map.put("isAdd", 1);
            } else {
                map.put("isAdd", 0);
            }
            //查找群信息
            Flock flock = mapper.queryByPrimaryKey(flockId);
            UserAccount userAccount = flock.getCreateUser();
            map.put("back", flock.getName());
            map.put("id", flockId);
            map.put("avatar", userAccount.getAvatar());
            map.put("createrId", userAccount.getUuid());
            map.put("flockState", flock.getState());
        }
        return map;

    }

    /**
     * 群功课统计
     *
     * @param list1
     */
    public List flockStatistic(List<FlockRecordVo> list1, Integer flockId,
                               String startTimes, String endTimes,
                               Integer pageNumber, Integer pageSize) {

        List list = new ArrayList<>();
        String tableName = null;
        String tableName1 = null;
        //系统功课和经书
//        String tableLessonName = "t_resource_content_book";
        //0:同年1：不是同年
        Integer year = 0;
        List<FlockRecordVo> flockList = null;
        FlockItem flockItem = null;


        for (FlockRecordVo vo : list1) {
            FlockRecordVo flockRecordVo = new FlockRecordVo();
            List<FlockRecordVo> flockRecordVoSum = null;
            Float numStr = new Float(0);
            if (vo.getFlockId() == null) {
                vo.setFlockId(flockId);
            }
            Flock flock = mapper.queryByPrimaryKey(flockId);
            flockRecordVo.setFlockName(flock.getName());
            if (StringUtils.isNotBlank(startTimes)) {
                flockRecordVo.setStartTimes(startTimes);
                Integer start = DateUtil.getTimeFormat(startTimes, DateUtil.DATE_PATTON_DEFAULT);
                vo.setStartTime(start);
            }
            if (StringUtils.isNotBlank(endTimes)) {
                flockRecordVo.setEndTimes(endTimes);
                String tims = DateUtil.getAfterAnyDay(endTimes, 1, DateUtil.DATE_PATTON_DEFAULT);
                Integer end = DateUtil.getTimeFormat(tims, DateUtil.DATE_PATTON_DEFAULT);
                vo.setEndTime(end);

            }
            if (StringUtils.isNotBlank(startTimes) && StringUtils.isNotBlank(endTimes)) {
                //判断统计时间是否同一年
                int a = DateUtil.getYear(endTimes) - DateUtil.getYear(startTimes);
                // a=0表示同年
                if (a == 0) {
                    tableName = "t_flock_record_" + DateUtil.getYear(startTimes);
                } else {
                    tableName = "t_flock_record_" + DateUtil.getYear(startTimes);
                    tableName1 = "t_flock_record_" + DateUtil.getYear(startTimes + 1);
                    year = 1;
                }
                //判断统计时间是否同一天0:同一天
                try {
                    int daysBetween = DateUtil.daysBetween(vo.getStartTime(), vo.getEndTime());
                    if (daysBetween == 0) {
                        String tims = DateUtil.getAfterAnyDay(startTimes, 1, DateUtil.DATE_PATTON_DEFAULT);
                        vo.setEndTime(DateUtil.getDateToLinuxTime(tims, DateUtil.DATE_PATTON_DEFAULT));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //查询当前用户的量词
//                String unit = unitService.queryUnitByUuidAndItemIdAndItemContentIdAndType(param.getUuid(), vo.getItemId(),
//                        vo.getItemContentId(), vo.getType());
                String unit = "";//StringUtils.isNotBlank(unit) ? unit : "遍";

                if (vo.getType() == 1) {
                    flockRecordVoSum = flockRecordService.findCustomSum(vo, tableName, tableName1, year);
                    if (flockRecordVoSum.size() > 0) {
                        double num = 0;
                        for (FlockRecordVo fl : flockRecordVoSum) {
                            if (fl != null && fl.getNum() != null) {
                                num = num + fl.getNum();
                            }
                        }
                        flockRecordVo.setNumStr(NumUtil.getTotalNumStr(num, "0") + unit);
                    } else {
                        flockRecordVo.setNumStr("0" + unit);
                    }
                    if (pageSize != null && pageNumber != null) {
                        PageHelper.startPage(pageSize, pageNumber);
                    }
                    flockList = flockRecordService.queryByCUstomIds(vo, tableName, tableName1, year);


                } else {
                    flockRecordVoSum = flockRecordService.findLessonSum(vo, tableName, tableName1, year);
                    if (flockRecordVoSum.size() > 0) {
                        double num = 0;
                        for (FlockRecordVo fl : flockRecordVoSum) {
                            if (fl != null && fl.getNum() != null) {
                                num = num + fl.getNum();
                            }
                        }
                        flockRecordVo.setNumStr(NumUtil.getTotalNumStr(num, "0") + unit);
                    } else {
                        flockRecordVo.setNumStr("0" + unit);
                    }
                    if (pageSize != null && pageNumber != null) {
                        PageHelper.startPage(pageSize, pageNumber);
                    }
                    flockList = flockRecordService.queryByIds(vo, tableName, tableName1, year);
                }
                for (FlockRecordVo recordVo : flockList) {
                    recordVo.setNumStr(NumUtil.getTotalNumStr(recordVo.getNum(), "0") + unit);
                }

                PageInfo pageInfo = new PageInfo(flockList);
                long totalPages = pageInfo.getPages();
                System.out.println(totalPages);
                //群功课名称
                if ((vo.getType() == 1)) {
                    flockRecordVo.setVersionName(vo.getLessonName());
                    flockRecordVo.setItemName(vo.getItemName());
                } else {
                    flockRecordVo.setVersionName(vo.getLessonName());
                    flockRecordVo.setItemName(vo.getItemName());
                }
                if (flockItem != null) {
                    flockRecordVo.setVersionName(flockItem.getLessonName());
                }
                flockRecordVo.setTotal(pageInfo.getTotal());
                flockRecordVo.setPeopleNum(pageInfo.getTotal());
                flockRecordVo.setTotalPages(totalPages);
                flockRecordVo.setFlockRecordVo(flockList);
                flockRecordVo.setItemContentId(vo.getItemContentId());
                flockRecordVo.setItemId(vo.getItemId());
                flockRecordVo.setType(vo.getType());
                flockRecordVo.setHasNextPage(pageInfo.isHasNextPage());
                flockRecordVo.setCurrentPage(pageSize);
                list.add(flockRecordVo);
            }

        }

        return list;
    }


    /**
     * 查找群功课
     * <p>
     * 查找群功课《群统计》
     *
     * @param flockId
     * @return
     */
    public List flockItemList(Integer flockId) {
        List<FlockItem> list = flockItemService.queryByFlockId(flockId);
        return list;
    }


    /**
     * 查看共修群简介回向
     *
     * @param flockId
     * @return
     */
    public Map<String, Object> queryFlockIntro(Integer flockId) {
        Map<String, Object> map = new HashMap<>();
        Flock flock = mapper.queryByPrimaryKey(flockId);
        map.put("intro", flock.getIntro() == null ? null : flock.getIntro());
        map.put("privacy", flock.getPrivacy());
        map.put("dedicationVerses", flock.getDedicationVerses());
        return map;
    }


    /**
     * 编辑更新群简介回向
     *
     * @param privacy
     * @param intro
     * @param dedicationVerses
     * @param flockId
     */
    @Transactional
    public void updateFlock(Integer privacy, String intro,
                            String dedicationVerses, Integer flockId) {
        if (privacy != null) {
            mapper.updateFlockPrivacy(flockId, privacy);
        }
        // 群简介
        if (StringUtils.isNotBlank(intro)) {
            mapper.updateFlockIntro(flockId, intro);
        }
        // 群回向
        if (StringUtils.isNotBlank(dedicationVerses)) {
            mapper.updateFlockDedicationVerses(flockId, dedicationVerses);
        }
    }


    /**
     * 统计时间
     *
     * @param flockId
     * @return
     */
    public Map<String, Object> flockStatisticTimes(Integer flockId) {
        Flock flock = mapper.queryFlockCreatedTimeById(flockId);
        Map<String, Object> map = new HashMap<>();
        map.put("startTimes", timestampToDates(flock.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT));
        map.put("endTimes", timestampToDates(DateUtil.currentSecond(), DateUtil.DATE_PATTON_DEFAULT));
        return map;
    }


    /**
     * 共修广场
     *
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords
     * @return
     */
    public Map<String, Object> queryJoinFlockList(Integer uuid,
                                                  Integer currentPage,
                                                  Integer pageSize,
                                                  Integer type,
                                                  String keywords) {
        Map<String, Object> maps = new HashMap<>();
        List list = new ArrayList();
        PageHelper.startPage(currentPage, pageSize);
        List<Flock> flockList = mapper.queryJoinFlockList(uuid, StringUtils.isNotBlank(keywords) ? keywords : null, type);
        if (flockList.size() != 0) {
            for (Flock flock : flockList) {
                Map<String, Object> map = new HashMap<>();
                flock.setViewStr(flock.getCreateUser().getNickname() +
                        "创建于 · " +
                        DateUtil.getTimeChaHour(DateUtil.formatIntDate(flock.getCreateTime()), new Date()));

                map.put("viewStr", flock.getViewStr());
                map.put("name", flock.getName());
                map.put("intro", flock.getIntro());
                map.put("privacy", flock.getPrivacy());
                map.put("uuid", flock.getCreateUser().getUuid());
                map.put("id", flock.getId());
                map.put("itemJoinNum", flock.getItemJoinNum());
                if (flock.getAvatarUrl() != null) {
                    flock.getCreateUser().setAvatar(flock.getAvatarUrl());
                    map.put("avatarUrl", flock.getAvatarUrl());
                } else {
                    map.put("avatarUrl", flock.getCreateUser().getAvatar());
                }
                list.add(map);
            }
        }
        PageInfo pageInfo = new PageInfo(flockList);
        long total = pageInfo.getTotal();
        long totalPages = pageInfo.getPages();
        maps.put("totalPages", totalPages);
        maps.put("total", total);
        maps.put("list", list);
        return maps;
    }


    /**
     * 共修群 排序
     *
     * @param flockList
     * @return
     */
    @Transactional
    public void updateFlockUserOrder(List<FlockRecordVo> flockList, Integer uuid) {
        if (flockList.size() != 0) {
            for (FlockRecordVo fl : flockList) {
                flockUserService.updateFlockUserOrder(fl.getFlockId(), fl.getOrder(), uuid);
            }
        }
    }


    /**
     * 共修群信息-《每日，周，月，年数据数状图》
     *
     * @param type
     * @return
     */
    public Map<String, Object> flockNumDetail(Integer type, Integer flockId, Integer createTime,
                                              Integer uuid, Double totalFlockNum) {
        Map<String, Object> map = new HashMap<>();
        //查找今日,本周，本月，本年冠军报数
        //查找我的今日,本周，本月，本年冠军报数
        String maxAvatar = "https://upd.everglamming.com:8089/group1/M00/00/13/wKgAHFoBUH-ARZFiAAC0d7SdfuU124.jpg";
        String maxPercent = "0%";
        String myAvatar = "https://upd.everglamming.com:8089/group1/M00/00/13/wKgAHFoBUH-ARZFiAAC0d7SdfuU124.jpg";
        String minePercent = "0%";
        List<FlockRecordVo> list = new ArrayList<>();
//        FlockRecordVo max = null;
        List<FlockRecordVo> recordList = new ArrayList<>();
        //群1下降；0上升；
        int compare = 0;
        //我的
        int mycompare = 0;
        //参与师兄人数
        Integer peopleNum = 0;
        //报数功课
        int itemNum = 0;
        //群今日报数
        String flockNumStr = "0";
        //群累计报数
        String flockNumTotalStr = "0";
        Double flockNumTotal = 0D;
        //我本日的报数
        String myNumStr = "0";
        double myNum = 0;
        //我上次的报数
        String numStr = "0";
        Integer num = 0;
        //我的排名
        Integer myIndex = 0;
        //群今日报数
        double flockNum = 0;
        //群与上次百分比
        String flockNumPre = "0%";
        //我与上次百分比
        String myNumPre = "0%";
        FlockRecordVo max = null;
        //我的上次报数
        double lastMyNum = 0;
        //群上次报数
        double lastFlockNum = 0;
        // 我的累计报数
        double totalMyNum = 0;
        String totalMyNumStr = "0";


        //群累计报数时间
        String time = null;


        //0:今日 1:本周 2:本月 3:本年
        //查找日每天数
        if (type == 3) {

//            list = flockRecordService.queryDayNumByFlockId(param.getId(), param.getType());
            Integer year = DateUtil.getYear();
            while (year >= 2018 && list.size() < 3) {
                FlockRecordVo vo = flockRecordService.queryYearNumByFlockIdAndTableName(flockId, "t_flock_record_" + year);
                if (vo != null) {
                    list.add(vo);
                }

                year--;
            }

        } else {
            int year = DateUtil.getYear();
            list = flockRecordService.queryDayNumByFlockId(flockId, type, "t_flock_record_" + year, "t_flock_record_" + (year - 1));

        }
        if (list.size() > 0) {

            if (createTime == null) {
                list.get(0).setState(true);
            }
//            list.get(list.size() - 1).setStartTimes(list.get(list.size() - 1).getCreateTimes());
            if (type == 1) {
                for (FlockRecordVo vo : list) {
                    String t = null;
                    String tt = null;
                    if (vo.getCreateTime() != null) {
                        t = DateUtil.timestampToDates(vo.getCreateTime(), DateUtil.TIME_PATTON_MMdd3);
                        tt = DateUtil.timestampToDates(vo.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD);
                    }
                    Map<String, Object> s = DateUtil.getDataPattonYyyymm(t, DateUtil.TIME_PATTON_MMdd3);
                    vo.setStartTimes((String) s.get("sunday"));
                    vo.setEndTimes(tt);
                    vo.setCreateTimes((String) s.get("sunday") + "-" + (String) s.get("saturday"));
                }
            }
            Optional<FlockRecordVo> optional = list.stream().max((o1, o2) -> o1.getNum().compareTo(o2.getNum()));

            FlockRecordVo maxVo = optional.isPresent() ? optional.get() : null;
            FlockRecordVo choseVO = list.get(0);
            flockNum = list.get(0).getNum() == null ? 0 : list.get(0).getNum();
            flockNumStr = NumUtil.getTotalNumStr((list.get(0).getNum() == null ? 0 : list.get(0).getNum()), "0");
            for (FlockRecordVo l : list) {
                l.setNumPre(NumUtil.getPercentForWechat(l.getNum(), maxVo.getNum()));
                l.setNumStr(NumUtil.getTotalNumStr((l.getNum() == null ? 0 : l.getNum()), "0"));

                if (createTime != null && l.getCreateTime().equals(createTime)) {
                    choseVO = l;
                    flockNumStr = l.getNumStr();
                    flockNum = l.getNum();
                    l.setState(true);
                }
            }
            int nums = flockRecordService.queryMyTotalNum(uuid, flockId);
            int years = DateUtil.getYear();

            List<FlockRecordVo> vos = flockRecordService.queryIsNotMyNum(uuid, type, flockId, choseVO.getCreateTime(), "t_flock_record_" + years, "t_flock_record_" + (years - 1));
            double sum = 0.0;
            if (vos.size() > 0) {
                sum = vos.stream().mapToDouble(FlockRecordVo::getNum).sum();//和
            }
            totalMyNum = nums - sum;
            totalMyNumStr = NumUtil.getTotalNumStr(totalMyNum, "0");

            String tableName = "t_flock_record_" + (DateUtil.getYear() < 2018 ? 2018 : DateUtil.getYear());

            if (type == 0) {
                time = DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
                String t = DateUtil.getBeforeDateFormatNew(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT), 1, DateUtil.DATE_PATTON_DEFAULT);
                tableName = "t_flock_record_" + (DateUtil.getYear(t) < 2018 ? 2018 : DateUtil.getYear(t));
            } else if (type == 1) {
                Map<String, Object> map1 = DateUtil.getDataPattonYyyymm(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.TIME_PATTON_MMdd3), DateUtil.TIME_PATTON_MMdd3);
                time = (String) map1.get("sunday") + "-" + (String) map1.get("saturday");

                String t = DateUtil.getBeforeDateFormatNew(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT), 7, DateUtil.DATE_PATTON_DEFAULT);
                tableName = "t_flock_record_" + (DateUtil.getYear(t) < 2018 ? 2018 : DateUtil.getYear(t));
            } else if (type == 2) {
                int c = DateUtil.getMonth(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2), DateUtil.DATA_PATTON_YYYYMMDD2);
                time = String.valueOf(c) + "月";
                String t = DateUtil.getSpecifiedMonthBefore(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT), DateUtil.DATE_PATTON_DEFAULT);
                tableName = "t_flock_record_" + (DateUtil.getYear(t) < 2018 ? 2018 : DateUtil.getYear(t));
            } else if (type == 3) {
                int c = DateUtil.getYear(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT));
                time = String.valueOf(c) + "年";
                int year = DateUtil.getYear(DateUtil.timestampToDates(choseVO.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT)) - 1;
                tableName = "t_flock_record_" + (year < 2018 ? 2018 : year);
            }
            //计算群本此数据与上一次数据比
            FlockRecordVo lastFlock = flockRecordService.queryLastFlockNum(flockId, choseVO.getCreateTime(), type, tableName);

            if (lastFlock != null && lastFlock.getNum() != null) {
                lastFlockNum = lastFlock.getNum();
            }
            if (flockNum - lastFlockNum < 0) {
                //1下降；0上升；
                compare = 1;
            }
            flockNumPre = NumUtil.getPercent1(Math.abs((flockNum - lastFlockNum)), lastFlockNum);
            String tableNames = "t_flock_record_" + DateUtil.getYear(timestampToDates(choseVO.getCreateTime(), DateUtil.DATE_PATTON_DEFAULT));

            //动态  groupby uuid  ---
            recordList = flockRecordService.queryRecordListByTime(type, flockId, choseVO.getCreateTime(), tableNames);


            if (recordList.size() != 0) {
                maxPercent = NumUtil.getPercentForWechat(recordList.get(0).getNum(), choseVO.getNum());
                maxAvatar = recordList.get(0).getAvatar();
                peopleNum = recordList.size();
//                boolean h = recordList.stream().anyMatch(x -> x.getUuid().equals(uuid));
//                if (!h) {
//                    myIndex = recordList.size() + 1;
//                }
                for (FlockRecordVo recordVo : recordList) {
                    recordVo.setNumStr(NumUtil.getTotalNumStr((recordVo.getNum() == null ? 0 : recordVo.getNum()), "0"));
                    if (recordVo.getUuid().equals(uuid)) {
                        myIndex = recordVo.getOrder();
                        myNumStr = recordVo.getNumStr();
                        myNum = recordVo.getNum();
                        minePercent = NumUtil.getPercentForWechat(recordVo.getNum(), choseVO.getNum());
                        myAvatar = recordVo.getAvatar();
                        FlockRecordVo vo = flockRecordService.queryLastMyNum(flockId, choseVO.getCreateTime(), type, uuid, tableName);
                        if (vo != null && vo.getNum() != null) {
                            lastMyNum = vo.getNum();
                        }
                        if ((recordVo.getNum() - lastMyNum) < 0) {
                            mycompare = 1;
                        }
                        myNumPre = NumUtil.getPercent1(Math.abs((recordVo.getNum() - lastMyNum)), lastMyNum);
                    }
                }
            }
            //本次报数功课数量
            itemNum = flockRecordService.queryFlockItemNum(flockId, type, choseVO.getCreateTime(), tableName);
            if (createTime != null) {
                flockNumTotal = totalFlockNum - (list.stream().filter(x -> x.getCreateTime() > createTime).count() > 0 ? list.stream().filter(x -> x.getCreateTime() > createTime).map(x -> x.getNum()).reduce(Double::sum).get() : 0);
            } else {
                flockNumTotal = totalFlockNum;
            }
            flockNumTotalStr = NumUtil.getTotalNumStr((flockNumTotal < 0 ? 0.0 : flockNumTotal), "0");


        }
        if (list.size() > 0 && list.size() < 10 && type == 0) {
            for (int i = list.size(); i < 10; i++) {
                FlockRecordVo vo = new FlockRecordVo();

                String tims = DateUtil.getBeforeDateFormatNew(list.get(list.size() - 1).getCreateTimes(), 1, DateUtil.TIME_PATTON_MMdd4);
                Integer tim = DateUtil.getDateToLinuxTime(tims, DateUtil.TIME_PATTON_MMdd4);
                vo.setCreateTimes(tims);
                vo.setCreateTime(tim);
                list.add(vo);

            }
        } else if (list.size() > 0 && list.size() < 4 && type == 1) {
            for (int i = list.size(); i < 4; i++) {
                FlockRecordVo vo = new FlockRecordVo();
                String tims = DateUtil.getBeforeDateFormatNew(list.get(list.size() - 1).getStartTimes(), 7, DateUtil.TIME_PATTON_MMdd3);
//                DateUtil.getDataPattonYyyymm(tims, DateUtil.TIME_PATTON_MMdd3);
                Map<String, Object> s = DateUtil.getDataPattonYyyymm(tims, DateUtil.TIME_PATTON_MMdd3);
                vo.setStartTimes((String) s.get("sunday"));
                vo.setCreateTimes((String) s.get("sunday") + "-" + (String) s.get("saturday"));
                String ti = DateUtil.getBeforeDateFormatNew(list.get(list.size() - 1).getEndTimes(), 7, DateUtil.DATA_PATTON_YYYYMMDD);
                vo.setEndTimes(ti);
                Integer tim = DateUtil.getDateToLinuxTime(ti, DateUtil.DATA_PATTON_YYYYMMDD);
//
                vo.setCreateTime(tim);
                list.add(vo);
            }
        } else if (list.size() > 0 && list.size() < 5 && type == 2) {
            for (int i = list.size(); i < 5; i++) {
                FlockRecordVo vo = new FlockRecordVo();

                //获取上一月
                String ti = DateUtil.timestampToDates(list.get(list.size() - 1).getCreateTime(), DateUtil.DATA_PATTON_MM);
                String tims = DateUtil.getSpecifiedMonthBefore(ti, DateUtil.DATA_PATTON_MM);
                vo.setCreateTimes(tims);

                String times = DateUtil.timestampToDates(list.get(list.size() - 1).getCreateTime(), DateUtil.DATA_PATTON_YYYYMM);
                String timss = DateUtil.getSpecifiedMonthBefore(times);
                Integer tim = DateUtil.getDateToLinuxTime(timss, DateUtil.DATA_PATTON_YYYYMM);
                vo.setCreateTime(tim);
                list.add(vo);
            }
        } else if (list.size() > 0 && list.size() < 3 && type == 3) {
            for (int i = list.size(); i < 3; i++) {
                FlockRecordVo vo = new FlockRecordVo();

                String tims = DateUtil.timestampToDates(list.get(list.size() - 1).getCreateTime(), DateUtil.DATE_PATTON_DEFAULT);
                Integer tim = DateUtil.getYear(tims);
                vo.setCreateTimes(String.valueOf(tim - 1));

                Integer ti = DateUtil.getDateToLinuxTime(String.valueOf(tim - 1), DateUtil.DATA_PATTON_YYYYMM3);
                vo.setCreateTime(ti);
                list.add(vo);
            }
        }

        List<FlockRecordVo> ascByIdList = list.stream().sorted(Comparator.comparing(FlockRecordVo::getCreateTime)).collect(Collectors.toList());
        map.put("minePercent", minePercent);
        map.put("maxAvatar", maxAvatar);
        map.put("maxPercent", maxPercent);
        map.put("myAvatar", myAvatar);
        map.put("list", ascByIdList);
        map.put("max", max);
        map.put("recordList", recordList);
        map.put("peopleNum", peopleNum);
        map.put("flockNumStr", flockNumStr);
        map.put("flockNum", flockNum);
        map.put("flockNumTotalStr", flockNumTotalStr);
        map.put("flockNumTotal", flockNumTotal);
        map.put("myNumStr", myNumStr);
        map.put("itemNum", itemNum);
        map.put("flockNumPre", flockNumPre);
        map.put("myNumPre", myNumPre);
        map.put("lastMyNum", lastMyNum);
        map.put("myCompare", mycompare);
        map.put("compare", compare);
        map.put("time", time);
        map.put("num", (num == null ? 0 : num));
        map.put("myIndex", myIndex);
        map.put("lastFlockNum", lastFlockNum);
        map.put("myNum", myNum);
        map.put("totalMyNumStr", totalMyNumStr);
        map.put("totalMyNum", totalMyNum);
        return map;
    }


    /**
     * 群详细《本周群数据和我的本周上周数据》
     *
     * @param uuid
     * @param flockId
     * @return
     */
    public Map<String, Object> flockWeekNumDetail(Integer uuid, Integer flockId) {
        Map<String, Object> map = new HashMap<>();

        //本周一时间戳
        Integer thisWeek = DateUtil.getTimesWeekmorning();
        //上周一时间戳
        Integer lastWeek = DateUtil.getTimesWeekmorning(-1);
        int year = DateUtil.getYear(DateUtil.timestampToDates(lastWeek, DateUtil.TIME_PATTON_DEFAULT));
        String tableName = "t_flock_record_" + (DateUtil.getYear() < 2018 ? 2018 : DateUtil.getYear());
        if (year != DateUtil.getYear()) {
            tableName = "t_flock_record_view";
        }
        FlockObjectDO flockRecord = flockRecordService.flockWeekNumDetailByFlockId(uuid, flockId, thisWeek, lastWeek, tableName);

        String flockThisTotal = "0";
        String myThisTotal = "0";
        double num = 0;
        //1下降；0上升；
        int compare = 0;
        String myNumPre = "0";
        String MyflockNumPre = "0";
        if (flockRecord != null) {
            //本周数据格式化
            flockThisTotal = NumUtil.getTotalNumStr(flockRecord.getFlockThisTotal(), "0");
            myThisTotal = NumUtil.getTotalNumStr(flockRecord.getMyThisTotal(), "0");
            //我的本周数据与上周数据比
            num = flockRecord.getMyThisTotal() - flockRecord.getMyLastTotal();
            if (num < 0) {
                compare = 1;
            }
            myNumPre = NumUtil.getPercent(Math.abs(num), flockRecord.getMyLastTotal());
            //我的本周数据与群本周数数比
            MyflockNumPre = NumUtil.getPercentForWechat(flockRecord.getMyThisTotal(), flockRecord.getFlockThisTotal());
        } else {
            flockRecord = new FlockObjectDO();
        }

        //群本周数据与上周数据比
        map.put("flockRecord", flockRecord);
        map.put("myNumPre", myNumPre);
        map.put("flockThisTotal", flockThisTotal);
        map.put("myThisTotal", myThisTotal);
        map.put("MyflockNumPre", MyflockNumPre);
        map.put("compare", compare);
        return map;
    }


    /**
     * 根据用户功课 查询群列表
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid          * @return
     */
    public Map<String, Object> itemFlockList(Integer itemId, Integer itemContentId,
                                             Integer type, Integer uuid) {
        Map<String, Object> map = new HashMap<>();
        List<Flock> flockList = mapper.queryByItemIdAndContentIdAndUuid(itemId, itemContentId, type, uuid);
        map.put("flockList", flockList);
        return map;
    }


    /**
     * 共修详情  共修群功课详情 卡片
     *
     * @param flockId
     * @param uuid
     * @return
     */
    public Map<String, Object> flockMessageCard(Integer flockId, Integer uuid, Integer itemId, Integer itemContentId, Integer type) {
        Map<String, Object> map = new HashMap<>();
        Flock flock = mapper.queryByPrimaryKey(flockId);
        String timeStr = DateUtil.timestampToDates(flock.getCreateTime(), DateUtil.DATA_PATTON_YYYYMMDD2);
        UserAccount createUser = flock.getCreateUser();
        if (createUser == null) {
            createUser = new UserAccount();
            createUser.setNickname("师兄");
            createUser.setAvatar("http://upd.everglamming.com:8089/group1/M00/00/13/wKgAHFoBUH-ARZFiAAC0d7SdfuU124.jpg");
        }
        map.put("isAdmin", uuid.equals(createUser.getUuid()) ? 1 : 0);
        map.put("lesson", lessonFacade.queryLessonByItemIdContentIdType(itemId, itemContentId, type));
        map.put("createName", createUser.getNickname());
        map.put("createAvatar", createUser.getAvatar());
        map.put("intro", flock.getIntro());
        map.put("flockId", flock.getId());
        map.put("privacy", flock.getPrivacy());
        map.put("timeStr", timeStr + "   " + DateUtil.dateToWeek(timeStr, DateUtil.DATA_PATTON_YYYYMMDD2));
        try {
            map.put("dayNum", DateUtil.daysBetween(flock.getCreateTime(), DateUtil.currentSecond()) + "天");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Flock queryByPrimaryKey(Integer flockId) {
        return mapper.queryByPrimaryKey(flockId);
    }

    public void updateFlockJoinNum(Integer flockId, int num) {
        mapper.updateFlockJoinNum(flockId, num);
    }

    public void logicDeleteFlock(Integer flockId, Integer flockState) {
        mapper.logicDeleteFlock(flockId, flockState);
    }


    @Transactional
    @LcnTransaction
    public Map<String, Object> flockCountReport(List<Integer> flockIds, Integer uuid, Integer itemId, Integer itemContentId, Integer type, Float lat, Float lon, Double num) {
        Integer recordId = lessonFacade.reportItem(num,type,uuid,itemId,itemContentId, 0);
        return flockReportCountService.flockCountReport(flockIds, uuid, recordId, itemId, itemContentId, type, lat, lon, num);
    }



    ///合并账号
    @Transactional
    @LcnTransaction
    public void mergeAccount(Integer uuid, Integer oldUuid) {
        mapper.updateUuidOfFlock(uuid, oldUuid);
        flockUserService.updateUuidOfFlockUser(uuid, oldUuid);
        flockItemUserCountService.updateUuidOfFlockItemCount(uuid, oldUuid);
        flockRecordService.updateUuidOfFlockRecord(uuid, oldUuid);
    }


}
