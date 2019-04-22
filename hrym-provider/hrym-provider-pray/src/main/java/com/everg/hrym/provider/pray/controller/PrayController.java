package com.everg.hrym.provider.pray.controller;

import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.pray.service.PrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/pray")
public class PrayController {


    @Autowired
    private PrayService prayService;

    /**
     * 创建 助念
     * @param voList
     * @return
     */
    @PostMapping("createPray")
    Map<String,Object> createPray(@RequestBody List<ItemVO> voList, @RequestParam("uuid")Integer uuid , @RequestParam("prayName")String prayName,
                                  @RequestParam(value = "dedicationVerses",required = false)String dedicationVerses,
                                  @RequestParam("privacy")Integer privacy, @RequestParam(value = "intro",required = false)String intro,
                                  @RequestParam("programType")Integer programType,@RequestParam("overMethod")Integer overMethod,@RequestParam("overTime")String overTime){

        return prayService.createPray(voList,uuid,prayName,dedicationVerses,privacy,intro,programType,overMethod,overTime);
    }



    /**
     * 详情页
     * @param prayId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("provider/pray/assistPrayDetails")
    Map<String,Object> assistPrayDetails(@RequestParam("prayId") Integer prayId,@RequestParam("uuid") Integer uuid,
                                         @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize){
        return prayService.assistPrayDetails(prayId,uuid,currentPage,pageSize);
    }
}
