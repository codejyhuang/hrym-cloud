package com.everg.hrym.provider.lesson.controller;

import com.everg.hrym.provider.lesson.service.ItemUserUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("provider/unit/")
public class ItemUnitController {

    @Autowired
    private ItemUserUnitService itemUserUnitService;

    /**
     * 查询功课量词
     *
     * @param  itemId
     * @param  itemContentId
     * @param  type
     * @param  uuid
     */
    @PostMapping("queryUnit")
    String queryUnitByUuidAndItemIdAndItemContentIdAndType(@RequestParam("uuid") Integer uuid,
                                                           @RequestParam("itemId") Integer itemId,
                                                           @RequestParam("itemContentId") Integer itemContentId,
                                                           @RequestParam("type") Integer type){
        return itemUserUnitService.queryUnitByUuidAndItemIdAndItemContentIdAndType(uuid,itemId,itemContentId,type);
    }
}
