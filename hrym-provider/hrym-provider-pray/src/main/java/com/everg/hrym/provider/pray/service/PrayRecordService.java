package com.everg.hrym.provider.pray.service;

import com.everg.hrym.api.pray.entity.AssistPrayItem;
import com.everg.hrym.api.pray.entity.AssistPrayRecordDO;
import com.everg.hrym.provider.pray.mapper.PrayRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrayRecordService {

    @Autowired
    private PrayRecordMapper mapper;

    public List<AssistPrayRecordDO> queryByAssistPrayId(Integer id, List<AssistPrayItem> lessonDOList, Integer uuid) {

        return mapper.queryByAssistPrayId(id, lessonDOList, uuid);
    }
}
