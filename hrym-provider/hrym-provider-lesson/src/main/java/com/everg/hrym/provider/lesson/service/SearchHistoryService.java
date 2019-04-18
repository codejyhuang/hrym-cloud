package com.everg.hrym.provider.lesson.service;

import com.everg.hrym.api.lesson.entity.SearchHistory;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.provider.lesson.mapper.SearchHistoryMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchHistoryService {

    @Autowired
    private SearchHistoryMapper mapper;

    /**
     * 查询 功课搜索历史记录
     *
     * @param uuid
     * @return
     */
    public List<SearchHistory> listByUuid(Integer uuid) {
        return mapper.listByUuid(uuid);
    }

    /**
     * 主键 删除搜索历史记录
     *
     * @param id
     */
    public void delSearchHistory(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 清空搜索历史记录
     *
     * @param uuid
     */
    @Transactional
    public void emptySearchHistory( Integer uuid) {
        mapper.emptySearchHistoryByUuid(uuid);
    }

    @Transactional
    public void deleteByPrimaryKey(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void insert(SearchHistory history) {
        mapper.insert(history);
    }

    /**
     * 搜索功课 创建功课历史记录 (抽取代码块)
     *
     * @param uuid
     * @param keywords
     */
    public void inserSearchHistory(Integer uuid, String keywords) {
        if (uuid != null && StringUtils.isNotBlank(keywords)) {
            List<SearchHistory> list = mapper.listByUuid(uuid);
            if (list.size() != 0) {
                boolean hasSame = list.stream().anyMatch(x -> x.getContent().equals(keywords));
                if (!hasSame) {
                    if (list.size() == 5) {
                        SearchHistory history = list.get(4);
                        mapper.deleteByPrimaryKey(history.getId());
                    }
                    SearchHistory history = new SearchHistory();
                    history.setContent(keywords);
                    history.setUuid(uuid);
                    history.setCreateTime(DateUtil.currentSecond());
                    mapper.insert(history);
                }
            } else {
                SearchHistory history = new SearchHistory();
                history.setContent(keywords);
                history.setUuid(uuid);
                history.setCreateTime(DateUtil.currentSecond());
                mapper.insert(history);
            }
        }
    }
}
