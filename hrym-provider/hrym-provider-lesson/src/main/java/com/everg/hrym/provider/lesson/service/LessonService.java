package com.everg.hrym.provider.lesson.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.facade.FlockItemFacade;
import com.everg.hrym.api.lesson.entity.*;
import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.lesson.exception.LessonResultCode;
import com.everg.hrym.provider.lesson.mapper.BookMapper;
import com.everg.hrym.provider.lesson.mapper.ItemCustomMapper;
import com.everg.hrym.provider.lesson.mapper.ItemLessonMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonService {
    @Autowired
    private ItemCustomMapper customMapper;

    @Autowired
    private ItemLessonMapper lessonMapper;
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private SearchHistoryService searchHistoryService;


    @Autowired
    private ItemUserUnitService userUnitService;


    @Autowired
    private FlockItemFacade flockItemFacade;

    /**
     * 根系功课  online_num
     *
     * @param list 功课集合
     */
    @Transactional
    @LcnTransaction
    public void updateOnlineNum(List<ItemVO> list) {
        for (ItemVO itemVO : list) {
            lessonMapper.updateOnlineNum(itemVO.getItemId(), itemVO.getItemContentId(), itemVO.getType(), itemVO.getCount());
        }
    }

    /**
     * record 集合
     *
     * @param itemId
     * @param privacy
     */
    @Transactional
    public void updateCustomPrivacy(Integer itemId, Integer privacy) {
        customMapper.updateCustomPrivacy(itemId, privacy);
    }

    /**
     * 创建自建功课
     *
     * @param uuid
     * @param lessonName
     * @param unit
     * @param intro
     * @param privacy
     */
    @Transactional
    public void buildCustom(Integer uuid, String lessonName,
                            String unit,
                            String intro,
                            String info,
                            Integer privacy) {
        int count = customMapper.queryCountByCustomName(lessonName, uuid);
        if (count != 0) {
            throw new HrymException(LessonResultCode.ERROR1);
        } else {
            ItemCustom custom = new ItemCustom();
            custom.setCreateTime(DateUtil.currentSecond());
            custom.setIntro(intro);
            custom.setUnit(unit);
            custom.setUserId(uuid);
            custom.setCustomName(lessonName);
            custom.setInfo(info);
            custom.setPrivacy(privacy);
            customMapper.buildCustom(custom);

            if (StringUtils.isNotBlank(unit) && !"遍".equals(unit.trim())) {
                ItemUserUnit itemUserUnit = new ItemUserUnit();
                itemUserUnit.setType(1);
                itemUserUnit.setUuid(uuid);
                itemUserUnit.setItemId(custom.getItemId());
                itemUserUnit.setUnit(unit);
                userUnitService.insert(itemUserUnit);
            }
        }
    }

    /**
     * 设置 量词
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param unit
     * @param uuid
     * @param intro
     */
    @Transactional
    public void updateLessonPrivacyUnitIntro(Integer itemId, Integer itemContentId,
                                             Integer type, Integer uuid,
                                             String unit, String intro) {
        ItemUserUnit itemUserUnit = new ItemUserUnit();

        itemUserUnit.setType(type);
        itemUserUnit.setItemContentId(itemContentId == null ? 0 : itemContentId);
        itemUserUnit.setUuid(uuid);
        itemUserUnit.setItemId(itemId);
        itemUserUnit.setUnit(unit);
        itemUserUnit.setIntro(intro);
        itemUserUnit.setUpdateTime(DateUtil.currentSecond());

        ItemUserUnit userUnit = userUnitService.queryItemUserUnit(uuid
                , itemId
                , itemContentId
                , type);

        if (userUnit != null) {
            userUnitService.updateItemUserUnitById(itemUserUnit);
        } else {
            userUnitService.insert(itemUserUnit);
        }
    }


    /**
     * 查询功课隐私量词
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     */
    public Map<String, Object> queryLessonPrivacyUnitIntro(Integer itemId, Integer itemContentId,
                                                           Integer type, Integer uuid) {
        Map<String, Object> map = new HashMap<>();
        ItemUserUnit userUnit = userUnitService.queryItemUserUnit(uuid
                , itemId
                , itemContentId
                , type);
        if (userUnit != null) {
            map.put("unit", userUnit.getUnit());
            map.put("intro", userUnit.getIntro());
            map.put("id", userUnit.getId());

        } else {
            LessonCommon lesson = queryLessonFromView(itemId, itemContentId, type);
            map.put("unit", (lesson.getUnit() == null ? "遍" : lesson.getUnit()));
            map.put("intro", lesson.getIntro());
            map.put("id", null);
        }
        map.put("itemId", itemId);
        map.put("itemContentId", itemContentId);
        map.put("type", type);
        return map;
    }

    /**
     * 导入历史记录
     *
     * @param num           上报数
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @param recordMethed
     * @param taskId        任务id
     */
    @CacheEvict(value = "myTask", key = "#uuid")
    public void importHistory(Double num, Integer itemId, Integer itemContentId,
                              Integer type, Integer uuid, Integer taskId,
                              Integer recordMethed) {
        // 判断前端报数是否大于零
        if (num != null && num >= 0) {
            if (type == 2) {
                Integer reportNum = 0;
                String percent = "0%";
                reportNum = num.intValue();
                TaskCommon bookTask = taskService.findBookTaskPlan(itemId, itemContentId);
                Integer historyNum = recordService.queryBookHistoryRecord(taskId);
                if (historyNum == null) {
                    RecordCommon record = new RecordCommon();
                    record.setTaskId(bookTask.getTaskId());
                    record.setUserId(uuid);
                    record.setReportNum(Long.valueOf(reportNum));
                    record.setPercent(percent);
                    record.setReportTime(DateUtil.currentSecond());
                    record.setItemId(itemId);
                    record.setRecordMethod(recordMethed);
                    //插入上报数据
                    recordService.insertBookRecord(record);
                    //第一次导入  直接更新 report_num ++
                    taskService.updateBookcaseReport(reportNum, percent, bookTask.getTaskId(), DateUtil.currentSecond());
                } else {
                    recordService.updateBookHistoryRecordNum(reportNum, taskId);
                    //修改导入数据
                    taskService.updateBookcaseReport(reportNum - historyNum, percent, bookTask.getTaskId(), DateUtil.currentSecond());
                }

            } else {
                long reportNum = num.longValue();
                if (type == 1) {
                    Long historyNum = recordService.queryCustomHistoryRecordNum(taskId);
                    if (historyNum == null) {
                        RecordCommon custom = new RecordCommon();
                        custom.setTaskId(taskId);
                        custom.setItemId(itemId);
                        custom.setUserId(uuid);
                        custom.setReportNum(reportNum);
                        custom.setRecordMethod(recordMethed);
                        custom.setReportTime(DateUtil.currentSecond());
                        custom.setRecordStatus(0);
                        recordService.saveCustomRecord(custom);  //首次保存  导入record
                        taskService.updateCustomReport(reportNum, DateUtil.currentSecond(), taskId);  //更新  task
                    } else {
                        recordService.updateCustomHistoryRecordNum(reportNum, taskId); //覆盖 导入 record
                        taskService.updateCustomReport(reportNum - historyNum, DateUtil.currentSecond(), taskId); //更新 task
                    }
                } else {
                    Long historyNum = recordService.queryLessonHistoryRecordNum(taskId);
                    if (historyNum == null) {
                        RecordCommon lesson = new RecordCommon();
                        lesson.setTaskId(taskId);
                        lesson.setItemId(itemId);
                        lesson.setItemContentId(itemContentId);
                        lesson.setUserId(uuid);
                        lesson.setReportNum(reportNum);
                        lesson.setRecordMethod(recordMethed);
                        lesson.setReportTime(DateUtil.currentSecond());
                        lesson.setRecordStatus(0);
                        recordService.saveLessonRecord(lesson); //首次保存  导入record
                        taskService.updateLessonReport(reportNum, DateUtil.currentSecond(), taskId);  //更新  task
                    } else {
                        recordService.updateLessonHistoryRecordNum(reportNum, taskId); //覆盖 导入 record
                        taskService.updateLessonReport(reportNum - historyNum, DateUtil.currentSecond(), taskId);  //更新 task
                    }
                }
            }
        }
    }

    /**
     * 查询 功课
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @return 功课实体
     */
    public LessonCommon queryLessonByItemIdContentIdType(Integer itemId,
                                                         Integer itemContentId,
                                                         Integer type) {
        return queryLessonFromView(itemId, itemContentId, type);
    }


    public int queryByCustomIdFromResourceCustom(Integer itemId, Integer userId) {
        return customMapper.queryByCustomIdFromResourceCustom(itemId, userId);
    }

    public LessonCommon queryLessonFromView(Integer itemId, Integer itemContentId, Integer type) {
        return lessonMapper.queryLessonFromView(itemId, itemContentId, type);
    }


    public List<RecordCommon> queryItemRecordList(Integer userId) {

        List<RecordCommon> commonList = lessonMapper.queryItemRecordList(userId);

        return commonList;
    }


    /**
     * 我的功课 列表 (功课入口进入)
     *
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords    * @return
     */
    public Map<String, Object> selfItemList(Integer uuid, Integer currentPage,
                                            Integer pageSize,
                                            Integer type,
                                            String keywords) {
        Map<String, Object> map = new HashMap<>();
        List<TaskCommon> taskList = taskService.getMyTask(uuid, null);
        PageHelper.startPage(currentPage, 10);
        List<LessonCommon> lessonList = lessonMapper.queryLessonListFromView(uuid, type, StringUtils.isNotBlank(keywords) ? keywords : null);
        for (LessonCommon lesson : lessonList) {
            lesson.setIsAdd(0);
            for (TaskCommon task : taskList) {
                if (task.getType().equals(lesson.getType())
                        && task.getItemId().equals(lesson.getItemId())
                        && task.getItemContentId().equals(lesson.getItemContentId())) {
                    lesson.setIsAdd(1);
                }
            }
        }
        PageInfo pageInfo = new PageInfo(lessonList);
        searchHistoryService.inserSearchHistory(uuid, keywords);
        map.put("list", lessonList);
        map.put("hasNextPage", pageInfo.isHasNextPage());
        map.put("totalPage", pageInfo.getPages());
        map.put("total", pageInfo.getTotal());
        return map;
    }



    public Map<String, Object> listAll(Integer uuid, Integer currentPage, Integer pageSize, Integer type, String keywords) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(currentPage, 10);
        List<LessonCommon> flockLessonDOList = lessonMapper.queryLessonListFromView(uuid, type, keywords);
        PageInfo pageInfo = new PageInfo(flockLessonDOList);
        searchHistoryService.inserSearchHistory(uuid,keywords);
        map.put("list", flockLessonDOList);
        map.put("hasNextPage", pageInfo.isHasNextPage());
        map.put("totalPage", pageInfo.getPages());
        map.put("total", pageInfo.getTotal());
        return map;
    }


    /**
     * 系统APP功课上报流程
     *
     * @param
     */
    @Transactional
    @LcnTransaction
    public Integer reportItem(Double num,
                              Integer type,
                              Integer uuid,
                              Integer itemId,
                              Integer itemContentId,
                              Integer recordMethod) {

        //判断是否已经创建task人物
        taskService.addTask(uuid, itemId, itemContentId, type);

        Integer recordId = null;

        //1：自建'0：系统功课；2：经书
        if (type == 2) {
            recordId = bookReport(num, uuid, itemId);

        } else {
            recordId = lessonReport(num, type, uuid, itemId, itemContentId, recordMethod);

        }
        return recordId;

    }

    /**
     * 功课上报
     *
     * @param
     */
    public Integer lessonReport(Double num, Integer type, Integer uuid, Integer itemId, Integer itemContentId, Integer recordMethod) {
        int statu = 0;
        long reportNum = Double.valueOf(num).longValue();
        Integer taskId = null;
        if (num > 0) {
            if (type == 1) {
                ItemCustom vo = customMapper.findTaskPlanCustomById(uuid, itemId);
                System.out.println(vo);
                taskId = vo.getTaskId();
                if (vo.getPlanTarget() != null && vo.getPlanTarget() > 0) {
                    if (vo.getPlanTarget() <= vo.getPlanTargetValue()) {
                        //已完成
                        customMapper.updateDoneNumByTaskId(reportNum, 0, DateUtil.currentSecond(), vo.getTaskId());

                    } else {
                        // 未完成
                        statu = 1;
                        customMapper.updateDoneNumByTaskId(reportNum, reportNum, DateUtil.currentSecond(), vo.getTaskId());
                        //功课计划完成清零
                        if ((vo.getPlanTargetValue() + reportNum) >= vo.getPlanTarget()) {
                            statu = 2;
                            ItemCustom custom = new ItemCustom();
                            //功课计划完成时间
                            custom.setCompeteTime(DateUtil.currentSecond());
                            custom.setTaskId(vo.getTaskId());
                            custom.setUpdateTime(DateUtil.currentSecond());
                            customMapper.updateTaskPlanCustom(custom);

                        }
                    }
                } else {
                    customMapper.updateDoneNumByTaskId(reportNum, 0, DateUtil.currentSecond(), vo.getTaskId());
                }
            } else {
                ItemLessonTaskPlan vo = lessonMapper.findTaskPlanLessonByTaskId(uuid, itemContentId, itemId);
                if (vo != null) {
                    taskId = vo.getTaskId();
                }
                if (vo != null && vo.getPlanTarget() != null && vo.getPlanTarget() > 0) {
                    if (vo.getPlanTargetValue() != null && vo.getPlanTarget() <= vo.getPlanTargetValue()) {
                        // 已完成
                        lessonMapper.updateDoneNumByTaskId(reportNum, 0, DateUtil.currentSecond(), 1, vo.getTaskId());
                    } else {
                        // 未完成
                        lessonMapper.updateDoneNumByTaskId(reportNum, reportNum, DateUtil.currentSecond(), 1, vo.getTaskId());
                        statu = 1;
                        //功课计划完成清零
                        if ((vo.getPlanTargetValue() + reportNum) >= vo.getPlanTarget()) {
                            //修行记录状态为已完成且有计划
                            statu = 2;

                            ItemLessonTaskPlan lesson = new ItemLessonTaskPlan();

                            //功课计划完成时间
                            lesson.setCompeteTime(DateUtil.currentSecond());
                            lesson.setTaskId(vo.getTaskId());
                            lesson.setUpdateTime(DateUtil.currentSecond());
                            lessonMapper.updateTaskPlanLesson(lesson);

                        }
                    }
                } else {
                    lessonMapper.updateDoneNumByTaskId(reportNum, 0, DateUtil.currentSecond(), 1, vo.getTaskId());
                }
            }
        }
        Integer recordId = record(num, type, uuid, itemId, itemContentId, statu, taskId, recordMethod);

        return recordId;
    }

    //上报历史记录//record
    public Integer record(Double num, Integer type, Integer uuid, Integer itemId, Integer itemContentId, Integer statu, Integer taskId, Integer recordMethod) {

        long reportNum = Double.valueOf(num).longValue();
        Integer recordId = null;
        // 判断前端报数是否大于零
        if (num >= 0) {
            if (type == 1) {
                RecordCommon custom = new RecordCommon();
                custom.setTaskId(taskId);
                custom.setItemId(itemId);
                custom.setUserId(uuid);
                custom.setReportNum(reportNum);
                //1手动报数
                if (recordMethod != null) {
                    custom.setRecordMethod(recordMethod);
                } else {
                    custom.setRecordMethod(0);
                }
                custom.setReportTime(DateUtil.currentSecond());
                custom.setRecordStatus(statu);

                customMapper.saveTaskRecord(custom);
                recordId = custom.getRecordId();
            } else {
                RecordCommon lesson = new RecordCommon();
                lesson.setTaskId(taskId);
                lesson.setItemId(itemId);
                lesson.setItemContentId(itemContentId);
                lesson.setUserId(uuid);
                lesson.setReportNum(reportNum);
                if (recordMethod != null) {
                    lesson.setRecordMethod(recordMethod);
                } else {
                    lesson.setRecordMethod(0);
                }
                lesson.setReportTime(DateUtil.currentSecond());
                lesson.setRecordStatus(statu);
                lessonMapper.saveTaskRecord(lesson);
                recordId = lesson.getRecordId();

            }
            //查找新插入后的ID
//            recordId = 1;
        }
        return recordId;
    }


    /**
     * 经书上报
     *
     * @param num
     */
    public Integer bookReport(Double num, Integer uuid, Integer itemId) {

        Integer reportNum = 0;
        String percent = "0%";
        if (num != null) {
            reportNum = Double.valueOf(num).intValue();
        }
        RecordCommon book = bookMapper.findFlockRecordBookByUuid(itemId, uuid);
        bookMapper.updateReport(reportNum, percent, book.getIndexId(), DateUtil.currentSecond());

        RecordCommon record = new RecordCommon();
        record.setTaskId(book.getIndexId());
        record.setUserId(uuid);
        record.setReportNum(Long.valueOf(reportNum));
        record.setPercent(percent);
        record.setReportTime(DateUtil.currentSecond());
        record.setItemId(itemId);
        record.setRecordMethod(0);
        //插入上报数据
        bookMapper.saveRecord(record);
        //获取新插入的ID
        Integer recordId = record.getRecordId();
        return recordId;
    }

    //////////////////合并账号 更新数据/////////////////

    public void updateUuidOfCustomItem(Integer uuid, Integer oldUuid) {
        customMapper.updateUuidOfCustomItem(uuid, oldUuid);
    }
}
