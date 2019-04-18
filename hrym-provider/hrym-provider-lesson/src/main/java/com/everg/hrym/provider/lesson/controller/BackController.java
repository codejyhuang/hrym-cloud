package com.everg.hrym.provider.lesson.controller;

import com.everg.hrym.provider.lesson.service.UserBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("provider/back")
public class BackController {

    @Autowired
    private UserBackService userBackService;

    /**
            * 粘贴上次回向
     * @param itemId
     * @return
             */
    @PostMapping(value = "lastSpecialBack")
    Map<String,Object> lastSpecialBack(@RequestParam("itemType") Integer itemType,
                                       @RequestParam("itemId")Integer itemId,
                                       @RequestParam("uuid")Integer uuid,
                                       @RequestParam("contentId")Integer contentId){
       return userBackService.lastSpecialBack(itemType,itemId,uuid,contentId);
    }


    @PostMapping(value = "updateSpecialBack")
    void updateSpecialBack(@RequestParam("id") Integer id,@RequestParam("info") String info){
        userBackService.updateSpecialBack(id,info);
    }

    @PostMapping(value = "querySpecialBackDetails")
    Map<String,Object> querySpecialBackDetails(@RequestParam("id") Integer id){
        return userBackService.querySpecialBackDetails(id);
    }


    @PostMapping(value = "queryDedicationVersesList")
    Map<String,Object> queryDedicationVersesList(@RequestParam("uuid") Integer uuid, @RequestParam("type") Integer type,
                                                 @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("flockId") Integer flockId, @RequestParam("year") Integer year){
        return userBackService.queryDedicationVersesList(uuid,type,currentPage,pageSize,flockId,year);
    }

    @PostMapping(value = "queryDedicationVerses")
    Map<String,Object> queryDedicationVerses(@RequestParam("id") Integer id){
        return userBackService.queryDedicationVerses(id);
    }

    @PostMapping(value = "queryDedicationVersesRecordList")
    Map<String,Object> queryDedicationVersesRecordList(@RequestParam("timeStr") String timeStr,
                                                       @RequestParam("currentPage") Integer currentPage,@RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam("flockId") Integer flockId,@RequestParam("ymd") String ymd){
        return userBackService.queryDedicationVersesRecordList(timeStr,currentPage,pageSize,flockId,ymd);
    }
}
