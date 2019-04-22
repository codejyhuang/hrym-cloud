package com.everg.hrym.provider.pray.service;

import com.everg.hrym.api.pray.entity.AssistPrayItemUserCount;
import com.everg.hrym.provider.pray.mapper.PrayItemUserCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrayItemUserCountService {

    @Autowired
    private PrayItemUserCountMapper mapper;

    public void save(AssistPrayItemUserCount assistPrayItemUserCount) {
        mapper.save(assistPrayItemUserCount);
    }
}
