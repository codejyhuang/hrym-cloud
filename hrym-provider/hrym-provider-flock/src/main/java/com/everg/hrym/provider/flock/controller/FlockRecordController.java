package com.everg.hrym.provider.flock.controller;

import com.everg.hrym.api.flock.entity.ParseRecord;
import com.everg.hrym.provider.flock.service.FlockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("provider/flockRecord")
public class FlockRecordController {

    @Autowired
    private FlockRecordService flockRecordService;

    /**
     * 删除 群 record 记录
     *
     * @param recordId
     * @param year
     * @return
     */
    @PostMapping("removeFlockRecord")
    void removeFlockRecord(@RequestParam("recordId") Integer recordId, @RequestParam("year") Integer year){
        flockRecordService.removeFlockRecord(recordId, year);
    }

    /**
     * 动态点赞
     *
     * @param id
     * @param year
     * @param uuid
     * @param type
     * @return
     */
    @PostMapping("parseRecord")
    void parseRecord(@RequestParam("id")Integer id,@RequestParam("year") Integer year,@RequestParam("uuid") Integer uuid,@RequestParam("type")Integer type){
        flockRecordService.parseRecord(id, year, uuid, type);
    }


    /**
     * 动态点赞
     *
     * @param recordId
     * @return
     */
    @PostMapping("queryLikeMember")
    List<ParseRecord> queryLikeMember(@RequestParam("recordId") Integer recordId){
       return flockRecordService.queryLikeMember(recordId);
    }
}
