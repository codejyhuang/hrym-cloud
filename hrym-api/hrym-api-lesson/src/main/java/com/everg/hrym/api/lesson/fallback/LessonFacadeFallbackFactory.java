package com.everg.hrym.api.lesson.fallback;

import com.everg.hrym.api.lesson.entity.LessonCommon;
import com.everg.hrym.api.lesson.entity.SearchHistory;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.common.support.smallProgram.QueryObjectParam;
import com.everg.hrym.common.support.vo.ItemVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class LessonFacadeFallbackFactory implements FallbackFactory<LessonFacade> {

    @Override
    public LessonFacade create(Throwable throwable) {
        return new LessonFacade() {

            @Override
            public void updateOnlineNum(List<ItemVO> list) {

            }

            @Override
            public void updateUnitIntro(Integer itemId, Integer itemContentId, Integer type, Integer uuid, String unit, String intro) {

            }

            @Override
            public Map<String, Object> queryLessonPrivacyUnitIntro(Integer itemId, Integer itemContentId, Integer type, Integer uuid) {
                return null;
            }


            @Override
            public LessonCommon queryLessonByItemIdContentIdType(Integer itemId, Integer itemContentId, Integer type) {
                return null;
            }

            @Override
            public Map<String, Object> selfItemList(Integer uuid, Integer currentPage, Integer pageSize, Integer type, String keywords) {
                return null;
            }


            @Override
            public Map<String, Object> listAll(Integer uuid, Integer currentPage, Integer pageSize, Integer type, String keywords) {
                return null;
            }


            @Override
            public void importHistory(Double num, Integer itemId, Integer itemContentId, Integer type, Integer uuid, Integer taskId, Integer recordMethed) {

            }


            @Override
            public Map<String, Object> selfRecordHistory(Integer itemId, Integer itemContentId, Integer type, Integer uuid, Integer currentPage, Integer pageSize, Integer taskId) {
                return null;
            }

            @Override
            public void updateCustomPrivacy(Integer itemId, Integer privacy) {

            }

            @Override
            public void removeSelfRecordHistory(Integer taskId, Integer type, Integer recordId, Integer num, String ymd, Integer uuid, Integer year) {

            }


            @Override
            public Integer reportItem(Double num, Integer type, Integer uuid, Integer itemId, Integer itemContentId, Integer recordMethod) {
                return null;
            }
    
            @Override
            public List<SearchHistory> listSearchHistory(Integer uuid) {
                return null;
            }

            @Override
            public void delSearchHistory(Integer id) {

            }

            @Override
            public void emptySearchHistory(Integer uuid) {

            }

            @Override
            public void buildCustom(Integer uuid, String lessonName, String unit, String intro, String info, Integer privacy) {

            }



        };
    }
}
