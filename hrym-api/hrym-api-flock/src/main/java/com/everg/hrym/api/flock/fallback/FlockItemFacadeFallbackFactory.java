package com.everg.hrym.api.flock.fallback;

import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.ParseRecord;
import com.everg.hrym.api.flock.facade.FlockItemFacade;
import com.everg.hrym.common.support.vo.ItemVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class FlockItemFacadeFallbackFactory implements FallbackFactory<FlockItemFacade> {

    @Override
    public FlockItemFacade create(Throwable throwable) {
        return new FlockItemFacade() {



            @Override
            public Map<String, Object> exportFlockItem(Integer userId) {
    
                return null;
            }

            @Override
            public Map<String, Object> flockMessageRecord(Integer currentPage, Integer pageSize, Integer flockId, Integer itemId, Integer itemContentId, Integer type, Integer timeType, Integer uuid) {
                return null;
            }


            @Override
            public void removeFlockRecord(Integer recordId, Integer year) {

            }

            @Override
            public void parseRecord(Integer id, Integer year, Integer uuid, Integer type) {

            }


            @Override
            public List<ParseRecord> queryLikeMember(Integer recordId) {
                return null;
            }


            @Override
            public Map<String, Object> flockMessageBack(Integer flockId, Integer uuid) {
                return null;
            }
    
            @Override
            public List<FlockItem> queryFlockItemList(Integer flockId) {
                return null;
            }

            @Override
            public Map<String, Object> lessonList(Integer uuid, Integer currentPage, Integer pageSize, Integer type, String keywords, Integer flockId) {
                return null;
            }


        };
    }
}
