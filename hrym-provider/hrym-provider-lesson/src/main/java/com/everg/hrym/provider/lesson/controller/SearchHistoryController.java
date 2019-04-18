package com.everg.hrym.provider.lesson.controller;

import com.everg.hrym.api.lesson.entity.SearchHistory;
import com.everg.hrym.provider.lesson.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("provider/searchHistory/")
public class SearchHistoryController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * 查询 功课搜索历史记录
     * @param uuid
     * @return
     */
    @PostMapping("listSearchHistory")
    List<SearchHistory> listSearchHistory(@RequestParam("uuid") Integer uuid){
       return searchHistoryService.listByUuid(uuid);
    }

    /**
     * 主键 删除搜索历史记录
     * @param id
     */
    @PostMapping("delSearchHistory")
    void delSearchHistory(@RequestParam("id") Integer id){
        searchHistoryService.deleteByPrimaryKey(id);
    }

    /**
     * 清空搜索历史记录
     * @param uuid
     */
    @PostMapping("emptySearchHistory")
    void emptySearchHistory(@RequestParam("uuid") Integer uuid){
        searchHistoryService.emptySearchHistory(uuid);
    }

}
