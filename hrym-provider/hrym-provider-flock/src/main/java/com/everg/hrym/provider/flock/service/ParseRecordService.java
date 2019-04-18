package com.everg.hrym.provider.flock.service;

import com.everg.hrym.api.flock.entity.ParseRecord;
import com.everg.hrym.provider.flock.mapper.ParseRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class ParseRecordService {

    @Autowired
    private ParseRecordMapper mapper;

    public ParseRecord queryByRelationIdAndRecordTypeAndFrom(Integer relationId, Integer userId, int i) {
        return mapper.queryByRelationIdAndRecordTypeAndFrom(relationId, userId, i);
    }

    public void insert(ParseRecord ps) {
        mapper.insert(ps);
    }

    public int cancelByRelationIdAndUuid(Integer relationId, Integer userId, int i) {
        return mapper.cancelByRelationIdAndUuid(relationId, userId, i);
    }

    public List<ParseRecord> queryLikeMember(Integer recordId, int programType) {
        return mapper.queryLikeMember(recordId, programType);
    }
}
