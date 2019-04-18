package com.everg.hrym.provider.flock.controller;

import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.flock.service.FlockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/flockItem")
public class FlockItemController {

    @Autowired
    private FlockItemService flockItemService;


    /**
     * 群添加功课  功课列表
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords
     * @param flockId
     * @return
     */
    @PostMapping("lessonList")
    Map<String,Object> lessonList(@RequestParam("uuid")Integer uuid ,@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("type") Integer type, @RequestParam(value = "keywords",required = false) String keywords,
                                  @RequestParam("flockId") Integer flockId){
       return flockItemService.lessonList(uuid,currentPage,pageSize,type,keywords,flockId);
    }

    @GetMapping("exportFlockItem")
    Map<String, Object> exportFlockItem(@RequestParam("flockId") Integer flockId){
       return flockItemService.exportFlockItem(flockId);
    }

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
    @PostMapping("flockMessageRecord")
    Map<String,Object> flockMessageRecord(@RequestParam("currentPage")Integer currentPage,@RequestParam("pageSize")Integer pageSize,
                                          @RequestParam("flockId")Integer flockId,@RequestParam("itemId")Integer itemId,
                                          @RequestParam("itemConetentId")Integer itemContentId,@RequestParam("type")Integer type,
                                          @RequestParam("timeType")Integer timeType,@RequestParam("uuid")Integer uuid){
        return flockItemService.flockMessageRecord(currentPage, pageSize, flockId, itemId, itemContentId, type, timeType, uuid);
    }





    @PostMapping("flockMessageBack")
    Map<String,Object> flockMessageBack(@RequestParam("flockId") Integer flockId,@RequestParam("uuid")Integer uuid){
       return flockItemService.flockMessageBack(flockId, uuid);
    }

    /**
     * 查询共修群功课
     * @param flockId
     * @return
     */
    @PostMapping("queryFlockItemList")
    List<FlockItem> queryFlockItemList(@RequestParam("flockId") Integer flockId){
        return flockItemService.queryFlockItemList(flockId);
    }
}
