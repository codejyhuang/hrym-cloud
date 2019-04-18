package com.everg.hrym.application.hgx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.facade.FlockFacade;
import com.everg.hrym.api.flock.facade.FlockItemFacade;
import com.everg.hrym.api.lesson.entity.RecordCommon;
import com.everg.hrym.api.lesson.entity.TaskCommon;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.common.core.annotation.Allowed;
import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.support.base.BaseConstants;
import com.everg.hrym.common.support.smallProgram.FlockCountReportParam;
import com.everg.hrym.common.support.smallProgram.ItemParam;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by hrym13 on 2018/4/27.
 */
@RestController
@RequestMapping("hrym/wechat/assignment")
public class FlockItemConsumerController {

    @Autowired
    private FlockItemFacade flockItemFacade;

    @Autowired
    private TaskFacade taskFacade;

    @Autowired
    private FlockFacade flockFacade;

    @Autowired
    private LessonFacade lessonFacade;


    /**
     * 新建群  列表群功课
     *
     * @param qo
     * @return
     */
    @RequestMapping("listAssignment")
    @Allowed
    public BaseResult listLesson(@RequestBody QueryObjectParam qo) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, lessonFacade.listAll(qo.getUuid(), qo.getCurrentPage(), qo.getPageSize(), qo.getType(), qo.getKeywords()));
    }


    /**
     * 群功课列表  管理群<群详情里>
     *
     * @param
     * @return
     */
    @RequestMapping("listFlockAssignment")
    @Allowed
    public BaseResult listFlockLesson(@RequestBody QueryObjectParam qo) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockItemFacade.lessonList(qo.getUuid(), qo.getCurrentPage(), qo.getPageSize(), qo.getType(), qo.getKeywords(), qo.getId()));
    }


    /**
     * 查看功课搜索历史记录
     *
     * @param param
     * @return
     */
    @RequestMapping("/listSearchHistory")
    @Allowed
    public BaseResult listSearchHistory(@RequestBody QueryObjectParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, lessonFacade.listSearchHistory(param.getUuid()));
    }


    /**
     * 删除搜索历史记录
     *
     * @param param
     * @return
     */
    @RequestMapping("/delSearchHistory")
    @Allowed
    public BaseResult delSearchHistory(@RequestBody QueryObjectParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        lessonFacade.delSearchHistory(param.getId());
        return new BaseResult(code, message, null);
    }

    /**
     * 删除搜索历史记录
     *
     * @param param
     * @return
     */
    @RequestMapping("/emptySearchHistory")
    @Allowed
    public BaseResult emptySearchHistory(@RequestBody QueryObjectParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        lessonFacade.emptySearchHistory(param.getUuid());
        return new BaseResult(code, message, null);
    }


    @RequestMapping("/updateitemListOrder")
    @Allowed
    public BaseResult updateitemListOrder(@RequestBody ItemParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        taskFacade.updateTaskOrder(param.getTaskList(), param.getUserId());
        return new BaseResult(code, message, null);

    }

    /**
     * 我的功课  功课列表接口
     *
     * @param
     * @return
     */
    @RequestMapping("/selfItemList")
    @Allowed
    public BaseResult selfItemList(@RequestBody QueryObjectParam qo) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, lessonFacade.selfItemList(qo.getUuid(), qo.getCurrentPage(), qo.getPageSize(), qo.getType(), qo.getKeywords()));
    }

    /**
     * 我的功课
     *
     * @param
     * @return
     */
    @RequestMapping("/selfItem")
    @Allowed
    public BaseResult selfItem(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        Map<String, Object> taskMap = taskFacade.myTask(param.getUserId());
        return new BaseResult(code, message, taskMap);
    }


    /**
     * 添加功课  add task
     *
     * @param
     * @return
     */
    @RequestMapping("/addTask")
    @Allowed
    public BaseResult addTask(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        taskFacade.addTask(param.getUserId(), param.getItemId(), param.getItemContentId(), param.getType());
        return new BaseResult(code, message, null);
    }


    /**
     * 删除功课  remove task
     *
     * @param
     * @return
     */
    @RequestMapping("/removeTask")
    @Allowed
    public BaseResult removeTask(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        taskFacade.removeTask(param.getId(), param.getType(), param.getUserId());
        return new BaseResult(code, message, null);
    }

    /**
     * 导入历史记录
     *
     * @param param
     * @return
     */
    @RequestMapping("/importHistory")
    @Allowed
    public BaseResult importingHistoryRecord(@RequestBody FlockCountReportParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        lessonFacade.importHistory(param.getNum(), param.getItemId(), param.getItemContentId(), param.getType(), param.getUuid(), param.getTaskId(), param.getRecordMethod());
        return new BaseResult(code, message, null);
    }

    /**
     * 自修详情卡片
     *
     * @param
     * @return
     */
    @RequestMapping("/selfRepairCard")
    @Allowed
    public BaseResult selfRepairCard(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, taskFacade.selfRepairCard(param.getItemId(), param.getItemContentId(), param.getType(), param.getId(), param.getUserId()));
    }

    /**
     * 自修详情 修行历史记录
     *
     * @param
     * @return
     */
    @RequestMapping("/selfRepairHistory")
    @Allowed
    public BaseResult selfRepairHistory(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        Map<String, Object> map = lessonFacade.selfRecordHistory(param.getItemId(), param.getItemContentId(), param.getType(),
                param.getUserId(), param.getCurrentPage(), param.getPageSize(), param.getId());
        return new BaseResult(code, message, map);
    }

    /**
     * 自修详情 修行历史记录
     *
     * @param
     * @return
     */
    @RequestMapping("/removeSelfRepairHistory")
    @Allowed
    public BaseResult removeSelfRepairHistory(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        //删除  record 更新 task
        lessonFacade.removeSelfRecordHistory(param.getId(), param.getType(), param.getRecordId(), param.getNum(), param.getYmd(), param.getUserId(), param.getYear());
        return new BaseResult(code, message, null);
    }


    /**
     * 共修详情 共修群功课详情卡片
     *
     * @param
     * @return
     */
    @RequestMapping("/flockItemMessageCard")
    @Allowed
    public BaseResult flockItemMessageCard(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        Map<String, Object> map = flockFacade.flockMessageCard(param.getFlockId(), param.getUserId(), param.getItemId(), param.getItemContentId(), param.getType());
        return new BaseResult(code, message, map);
    }

    /**
     * 动态 点赞
     *
     * @param
     * @return
     */
    @RequestMapping("/parseRecord")
    @Allowed
    public BaseResult parseRecord(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        flockItemFacade.parseRecord(param.getId(), param.getYear(), param.getUserId(), param.getType());
        return new BaseResult(code, message, null);
    }


    /**
     * 共修详情 共修群功课  可点赞动态
     *
     * @param
     * @return
     */
    @RequestMapping("/flockItemMessageRecord")
    @Allowed
    public BaseResult flockItemMessageRecord(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        Map<String, Object> map = flockItemFacade.flockMessageRecord(param.getCurrentPage(), param.getPageSize(), param.getFlockId(),
                param.getItemId(), param.getItemContentId(), param.getType(), param.getTimeType(), param.getUserId()); //查询 年月日 上报数据
        return new BaseResult(code, message, map);
    }

    /**
     * 共修详情 点赞用户列表
     *
     * @param
     * @return
     */
    @RequestMapping("/queryLikeMember")
    @Allowed
    public BaseResult queryLikeMember(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockItemFacade.queryLikeMember(param.getId()));
    }

    /**
     * 创建自建功课
     *
     * @param
     * @return
     */
    @RequestMapping("buildCustom")
    @Allowed
    public BaseResult buildCustom(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        lessonFacade.buildCustom(param.getUserId(), param.getLessonName(), param.getUnit(), param.getIntro(), param.getInfo(), param.getPrivacy());
        return new BaseResult(code, message, null);
    }

    /**
     * 查询功课
     *
     * @param
     * @return
     */
    @RequestMapping("queryCustomPrivacy")
    @Allowed
    public BaseResult queryCustomPrivacy(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, lessonFacade.queryLessonByItemIdContentIdType(param.getItemId(), param.getItemContentId(), param.getType()));
    }


    /**
     * 更新 自建功课隐私
     *
     * @param
     * @return
     */
    @RequestMapping("updateCustomPrivacy")
    @Allowed
    public BaseResult updateCustomPrivacy(@RequestBody ItemParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        lessonFacade.updateCustomPrivacy(param.getItemId(), param.getPrivacy());
        return new BaseResult(code, message, null);
    }


    /**
     * 我的功课备份
     *
     * @param request
     * @param response
     * @param userId
     * @throws UnsupportedEncodingException
     */
    //文件下载：导出excel表
    @RequestMapping(value = "/exportExcel")
    @Allowed
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("userId") Integer userId) throws UnsupportedEncodingException {
        //一、从后台拿数据
        if (null == request || null == response) {
            return;
        }

        Map<String, Object> map = taskFacade.exportExcel(userId);

        //二、 数据转成excel
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");

        String fileName = "用户数据备份.xlsx";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建第一个Sheet页
        XSSFSheet sheet = wb.createSheet("用户总计");
        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet.setColumnWidth(0, 4000);//设置列宽
        sheet.setColumnWidth(1, 5500);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 5500);

        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("用户昵称 ");
        cell = row.createCell(1);
        cell.setCellValue("修行时间 ");
        cell = row.createCell(2);
        cell.setCellValue("功课门数");
        cell = row.createCell(3);
        cell.setCellValue("累计报数");
        cell = row.createCell(4);

        XSSFRow rows;
        XSSFCell cells;
        // 第三步：在这个sheet页里创建一行
        rows = sheet.createRow(1);
        // 第四步：在该行创建一个单元格
        cells = rows.createCell(0);
        // 第五步：在该单元格里设置值
        cells.setCellValue((String) map.get("nickName"));

        cells = rows.createCell(1);
        cells.setCellValue((Integer) map.get("timeStr"));
        cells = rows.createCell(2);
        cells.setCellValue((Integer) map.get("count"));
        cells = rows.createCell(3);
        cells.setCellValue((Integer) map.get("doneNum"));
        cells = rows.createCell(4);

        // 第二步：创建第二个Sheet页
        XSSFSheet sheet1 = wb.createSheet("用户功课详细");
        sheet1.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet1.setColumnWidth(0, 4000);//设置列宽
        sheet1.setColumnWidth(1, 5500);
        sheet1.setColumnWidth(2, 5500);
        sheet1.setColumnWidth(3, 5500);
        sheet1.setColumnWidth(11, 3000);
        sheet1.setColumnWidth(12, 3000);
        sheet1.setColumnWidth(13, 3000);
        XSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short) 16);

        XSSFRow row1 = sheet1.createRow(0);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("功课名称 ");
        cell1 = row1.createCell(1);
        cell1.setCellValue("累计报数 ");
        cell1 = row1.createCell(2);
        cell1.setCellValue("功课状态");

        XSSFRow rows1;
        XSSFCell cells1;
        List<TaskCommon> commonList = JSONObject.parseArray(JSON.toJSONString(map.get("taskCommonList")), TaskCommon.class);
        for (TaskCommon taskCommon : commonList) {
            // 第三步：在这个sheet页里创建一行

            rows1 = sheet1.createRow(commonList.indexOf(taskCommon) + 1);
            // 第四步：在该行创建一个单元格
            cells1 = rows1.createCell(0);
            // 第五步：在该单元格里设置值
            cells1.setCellValue(taskCommon.getLessonName());

            cells1 = rows1.createCell(1);
            cells1.setCellValue(taskCommon.getDoneNum());
            cells1 = rows1.createCell(2);
            if (taskCommon.getIsExit() != null && taskCommon.getIsExit() == 1) {
                cells1.setCellValue("未删除");

            } else {
                cells1.setCellValue("删除");
            }
        }
        // 第二步：创建第三个Sheet页
        XSSFSheet sheet2 = wb.createSheet("报数详细");
        sheet2.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet2.setColumnWidth(0, 4000);//设置列宽
        sheet2.setColumnWidth(1, 5500);
        sheet2.setColumnWidth(2, 5500);
        sheet2.setColumnWidth(3, 5500);
        XSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 16);

        XSSFRow row2 = sheet2.createRow(0);
        XSSFCell cell2 = row2.createCell(0);
        cell2.setCellValue("功课名称 ");
        cell2 = row2.createCell(1);
        cell2.setCellValue("报数 ");
        cell2 = row2.createCell(2);
        cell2.setCellValue("报数时间");

        XSSFRow rows2;
        XSSFCell cells2;
        List<RecordCommon> recordCommonList = JSONObject.parseArray(JSON.toJSONString(map.get("recordCommonList")), RecordCommon.class);
        for (RecordCommon record : recordCommonList) {
            // 第三步：在这个sheet页里创建一行
            rows2 = sheet2.createRow(recordCommonList.indexOf(record) + 1);
            // 第四步：在该行创建一个单元格
            cells2 = rows2.createCell(0);
            // 第五步：在该单元格里设置值
            cells2.setCellValue(record.getLessonName());

            cells2 = rows2.createCell(1);
            cells2.setCellValue(record.getReportNum());
            cells2 = rows2.createCell(2);
            cells2.setCellValue(record.getTimeStr());
        }


        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 群功课备份
     */
    @RequestMapping(value = "/exportFlockItem")
    @Allowed
    public void exportFlockItem(HttpServletRequest request, HttpServletResponse response, @RequestParam("userId") Integer userId, @RequestParam("flockId") Integer flockId) throws UnsupportedEncodingException {

        //一、从后台拿数据
        if (null == request || null == response) {
            return;
        }

        Map<String, Object> map = flockItemFacade.exportFlockItem(flockId);
//        System.out.println(map);

        List<FlockItem> flockItemName = JSONObject.parseArray(JSONObject.toJSONString(map.get("flockItemName")), FlockItem.class);
        //(List<FlockItem>) map.get("flockItemName");
        //二、 数据转成excel
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");

        String fileName = "共修群用户数据备份.xlsx";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
//        //设置居中格式
//        XSSFCellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
        // 第二步：创建第一个Sheet页
        XSSFSheet sheet = wb.createSheet("群汇总");
        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet.setColumnWidth(0, 4000);//设置列宽
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 4000);
        sheet.setColumnWidth(10, 4000);
        sheet.setColumnWidth(11, 4000);
        sheet.setColumnWidth(12, 4000);

        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        //居中
//        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFCellStyle alignStyle = (XSSFCellStyle) wb.createCellStyle();
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);


        XSSFRow row = sheet.createRow(0);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("群名称 ");
        cell = row.createCell(1);
        cell.setCellValue("时间 ");
        int i = 0;
        for (FlockItem item : flockItemName) {
            cell = row.createCell(2 + i);
            cell.setCellValue(item.getLessonName());
            i = i + 1;
        }
        cell = row.createCell(i + 2);
        cell.setCellValue("合计");
//        sheet.addMergedRegion(new CellRangeAddress(i+3,i+3,0,2));
//        CellRangeAddress cellRangeAddress=new CellRangeAddress(i+3,i+3,0,2);

//        cell = row.createCell(6);

        XSSFRow rows;
        XSSFCell cells;
        List<Map<String, Object>> flockItems = (List<Map<String, Object>>) map.get("flockItems");
        for (Map<String, Object> maps : flockItems) {
            System.out.println(maps.get("sum"));
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(flockItems.indexOf(maps) + 1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue((String) map.get("name"));
            cells = rows.createCell(1);
            cells.setCellValue((String) maps.get("timeStr"));
//            cells = rows.createCell(2);
            int i1 = 0;
            for (FlockItem item : flockItemName) {
                cells = rows.createCell(2 + i1);
                if (maps.get(item.getId() + "sums") != null) {
                    cells.setCellValue((Double) maps.get(item.getId() + "sums"));
                } else {
                    cells.setCellValue(0);

                }
//                cell = row.createCell(2+i);
//                cell.setCellValue(item.getLessonName());
                i1 = i1 + 1;
            }
            cells = rows.createCell(2 + i1);
            cells.setCellValue((Double) maps.get("sum"));

        }
        //最后一行统计&& 创建一行
        rows = sheet.createRow(flockItems.size() + 1);
        sheet.addMergedRegion(new CellRangeAddress(flockItems.size() + 1, flockItems.size() + 1, 0, 1));
        Map<String, Object> itemToTal = (Map<String, Object>) map.get("itemToTal");
        //创建合并单元格的最后一行
        cells = rows.createCell(0);
        cells.setCellValue("合计");
        int ii = 0;
        for (FlockItem item : flockItemName) {
            cells = rows.createCell(2 + ii);
            if (itemToTal.get(item.getId() + "total") != null) {
                cells.setCellValue((Double) itemToTal.get(item.getId() + "total"));
            } else {
                cells.setCellValue(0);

            }

            ii = ii + 1;
        }
        cells = rows.createCell(2 + ii);
        cells.setCellValue((Double) itemToTal.get("totalDoneNum"));


        // 第二步：创建第二个Sheet页
        XSSFSheet sheet1 = wb.createSheet("用户");
        sheet1.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet1.setColumnWidth(0, 5000);//设置列宽
        sheet1.setColumnWidth(1, 5000);
        sheet1.setColumnWidth(2, 4500);
        sheet1.setColumnWidth(3, 4500);
        sheet1.setColumnWidth(4, 4500);
        sheet1.setColumnWidth(5, 4500);
        sheet1.setColumnWidth(6, 4500);
        sheet1.setColumnWidth(7, 4500);//设置列宽
        sheet1.setColumnWidth(8, 4500);
        sheet1.setColumnWidth(9, 4500);
        sheet1.setColumnWidth(10, 4500);
        sheet1.setColumnWidth(11, 4500);
        sheet1.setColumnWidth(12, 4500);
        sheet1.setColumnWidth(13, 4500);
        //单元格样式
        XSSFCellStyle cellStyle1 = wb.createCellStyle();
//        cellStyle1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
        XSSFFont font1 = wb.createFont();
        font1.setFontName("黑体");
        font1.setFontHeightInPoints((short) 16);

        XSSFRow row1 = sheet1.createRow(0);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("群名称 ");
        cell1 = row1.createCell(1);
        cell1.setCellValue("时间 ");
        cell1 = row1.createCell(2);
        cell1.setCellValue("姓名");
        int i2 = 0;
        for (FlockItem item : flockItemName) {
            cell1 = row1.createCell(3 + i2);
            cell1.setCellValue(item.getLessonName());
            i2 = i2 + 1;
        }
        cell1 = row1.createCell(3 + i2);
        cell1.setCellValue("合计");

        XSSFRow rows1;
        XSSFCell cells1;
        List<Map<String, Object>> commonList = (List<Map<String, Object>>) map.get("userItemData");
        for (Map<String, Object> map1 : commonList) {
            // 第三步：在这个sheet页里创建一行

            rows1 = sheet1.createRow(commonList.indexOf(map1) + 1);
            // 第四步：在该行创建一个单元格
            cells1 = rows1.createCell(0);
            // 第五步：在该单元格里设置值
            cells1.setCellValue((String) map1.get("name"));
            cells1 = rows1.createCell(1);
            cells1.setCellValue((String) map1.get("time"));
            cells1 = rows1.createCell(2);
            cells1.setCellValue((String) map1.get("nickname"));
            int i1 = 0;
            for (FlockItem item : flockItemName) {
                cells1 = rows1.createCell(3 + i1);
                // 第五步：在该单元格里设置值
                if (map1.get(item.getId() + "total") == null) {
                    cells1.setCellValue(0);
                } else {
                    cells1.setCellValue((Double) map1.get(item.getId() + "total"));
                }
                i1 = i1 + 1;
            }
            cells1 = rows1.createCell(3 + i1);
            cells1.setCellValue((Double) map1.get("sum"));
        }
        //最后一行统计&& 创建一行
        rows1 = sheet1.createRow(commonList.size() + 1);
        sheet1.addMergedRegion(new CellRangeAddress(commonList.size() + 1, commonList.size() + 1, 0, 2));
//        Map<String,Object> itemToTal = (Map<String, Object>) map.get("itemToTal");
        //创建合并单元格的最后一行
        cells1 = rows1.createCell(0);
        cells1.setCellValue("合计");
        int iii = 0;
        for (FlockItem item : flockItemName) {
            cells1 = rows1.createCell(3 + iii);
            if (itemToTal.get(item.getId() + "total") != null) {
                cells1.setCellValue((Double) itemToTal.get(item.getId() + "total"));
            } else {
                cells1.setCellValue(0);

            }
//                cell = row.createCell(2+i);
//                cell.setCellValue(item.getLessonName());
            iii = iii + 1;
        }
        cells1 = rows1.createCell(3 + ii);
        cells1.setCellValue((Double) itemToTal.get("totalDoneNum"));


        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 报数
     *
     * @param
     * @return
     */
    @RequestMapping("/flockCountReport")
    @Allowed
    public BaseResult flockCountReport(@RequestBody FlockCountReportParam param) {

        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockFacade.flockCountReport(param.getFlockIds(), param.getUuid(), param.getItemId()
                , param.getItemContentId(), param.getType(), param.getLat(), param.getLon(), param.getNum()));
    }

    /**
     * 查找群回向数量和我的特别回向
     *
     * @param
     * @return
     */
    @RequestMapping("/flockMessageBack")
    @Allowed
    public BaseResult flockMessageBack(@RequestBody FlockCountReportParam param) {
        String code = BaseConstants.GWSCODE0000;
        String message = BaseConstants.GWSMSG0000;
        return new BaseResult(code, message, flockItemFacade.flockMessageBack(param.getFlockId(), param.getUuid()));
    }


}
