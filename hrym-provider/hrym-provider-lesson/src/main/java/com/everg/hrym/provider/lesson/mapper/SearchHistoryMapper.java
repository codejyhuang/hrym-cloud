package com.everg.hrym.provider.lesson.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.everg.hrym.api.lesson.entity.SearchHistory;

import java.util.List;

public interface SearchHistoryMapper {

    void insert(SearchHistory history);

    @DS("slave")
    List<SearchHistory> listByUuid(Integer uuid);

    void deleteByPrimaryKey(Integer id);

    void emptySearchHistoryByUuid(Integer uuid);
}
