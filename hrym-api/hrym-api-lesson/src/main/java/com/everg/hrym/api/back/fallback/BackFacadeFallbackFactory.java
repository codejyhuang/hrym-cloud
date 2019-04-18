package com.everg.hrym.api.back.fallback;

import com.everg.hrym.api.back.facade.BackFacade;
import com.everg.hrym.common.support.smallProgram.FlockUserBackParam;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class BackFacadeFallbackFactory implements FallbackFactory<BackFacade> {

    @Override
    public BackFacade create(Throwable throwable) {
        return new BackFacade() {
    
            @Override
            public Map<String, Object> lastSpecialBack(Integer itemType, Integer itemId, Integer uuid, Integer contentId) {
                return null;
            }
    
            @Override
            public void insertSpecialBack(FlockUserBackParam param) {
        
            }
    
            @Override
            public void updateSpecialBack(Integer id, String info) {
        
            }
            
    
            @Override
            public Map<String, Object> querySpecialBackDetails(Integer flockId) {
                return null;
            }
    
    
            @Override
            public Map<String, Object> queryCountDirection(FlockUserBackParam param) {
                return null;
            }
    
            @Override
            public void deleteSpecialBack(FlockUserBackParam param) {
        
            }
    
            @Override
            public Map<String, Object> queryDedicationVersesList(Integer uuid, Integer type, Integer currentPage, Integer pageSize, Integer flockId, Integer year) {
                return null;
            }
    
            @Override
            public Map<String, Object> queryDedicationVerses(Integer id) {
                return null;
            }
    
            @Override
            public Map<String, Object> queryDedicationVersesRecordList( String timeStr, Integer currentPage, Integer pageSize, Integer flockId, String ymd) {
                return null;
            }
    
        };
    }
}
