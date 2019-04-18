package com.everg.hrym.provider.lesson.controller;

import com.everg.hrym.provider.lesson.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("provider/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * record 集合
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param taskId
     * */
    @PostMapping("selfRecordHistory")
    Map<String, Object> selfRecordHistory(@RequestParam("itemId")Integer itemId, @RequestParam("itemContentId")Integer itemContentId, @RequestParam("type")Integer type,
                                          @RequestParam("uuid")Integer uuid, @RequestParam("currentPage")Integer currentPage, @RequestParam("pageSize")Integer pageSize,
                                          @RequestParam("taskId")Integer taskId){
        return recordService.selfRecordHistory(itemId,itemContentId,type,uuid,currentPage,pageSize,taskId);
    }


    /**
     * record 集合
     *
     * @param taskId
     * @param type
     * @param recordId
     * @param num
     */
    @PostMapping("removeSelfRecordHistory")
    void removeSelfRecordHistory(@RequestParam("taskId") Integer taskId,
                                 @RequestParam("type") Integer type,
                                 @RequestParam("recordId") Integer recordId,
                                 @RequestParam("num") Integer num,
                                 @RequestParam("ymd") String ymd,
                                 @RequestParam("uuid")Integer uuid,@RequestParam("year")Integer year){
        recordService.removeSelfRecordHistory(taskId,type,recordId,num,ymd,uuid,year);
    }

}
