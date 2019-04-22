package com.everg.hrym.provider.pray.service;

import com.everg.hrym.api.pray.entity.AssistPrayUser;
import com.everg.hrym.provider.pray.mapper.PrayUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrayUserService {

    @Autowired
    private PrayUserMapper mapper;

    public void insert(AssistPrayUser assistPrayUser) {
        mapper.insert(assistPrayUser);
    }

    public List<Integer> queryUuidListByAssistPrayId(Integer prayId) {
        return mapper.queryUuidListByAssistPrayId(prayId);
    }
}
