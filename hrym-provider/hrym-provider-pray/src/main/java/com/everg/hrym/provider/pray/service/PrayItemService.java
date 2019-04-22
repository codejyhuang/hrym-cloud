package com.everg.hrym.provider.pray.service;

import com.everg.hrym.api.pray.entity.AssistPrayItem;
import com.everg.hrym.provider.pray.mapper.PrayItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrayItemService {

    @Autowired
    private PrayItemMapper mapper;

    public void insert(AssistPrayItem assistPrayItem) {
        mapper.insert(assistPrayItem);
    }


    public List<AssistPrayItem> queryByAssistPrayIdJoinView(Integer id, Integer uuid) {
        return queryByAssistPrayIdJoinView(id,uuid);
    }
}
