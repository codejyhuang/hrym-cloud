package com.everg.hrym.api.flock.fallback;

import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.facade.FlockFacade;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import com.everg.hrym.common.support.vo.ItemVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class FlockFacadeFallbackFactory implements FallbackFactory<FlockFacade> {

    @Override
    public FlockFacade create(Throwable throwable) {
        return new FlockFacade() {


            @Override
            public List<FlockItem> flockItemList(Integer flockId) {
                return null;
            }

            @Override
            public Map<String, Object> queryFlockIntro(Integer flockId) {
                return null;
            }

            @Override
            public void updateFlock(Integer privacy, String intro, String dedicationVerses, Integer flockId) {

            }

            @Override
            public Map<String, Object> flockStatisticTimes(Integer flockId) {
                return null;
            }

            @Override
            public Map<String, Object> queryJoinFlockList(Integer uuid, Integer currentPage, Integer pageSize, Integer type, String keywords) {
                return null;
            }

            @Override
            public void updateFlockUserOrder(List<FlockRecordVo> flockList, Integer uuid) {

            }

            @Override
            public Map<String, Object> flockNumDetail(Integer type, Integer flockId, Integer createTime, Integer uuid, Double totalFlockNum) {
                return null;
            }

            @Override
            public Map<String, Object> flockWeekNumDetail(Integer uuid, Integer flockId) {
                return null;
            }

            @Override
            public Map<String, Object> itemFlockList(Integer itemId, Integer itemContentId, Integer type, Integer uuid) {
                return null;
            }

            @Override
            public Map<String, Object> flockMessageCard(Integer flockId, Integer uuid, Integer itemId, Integer itemContentId, Integer type) {
                return null;
            }

            @Override
            public void mergeAccount(Integer uuid, Integer oldUuid) {

            }


            @Override
            public Map<String, Object> createFlock(List<ItemVO> voList, Integer uuid, String flockName, String dedicationVerses, Integer privacy, String intro) {
                return null;
            }

            @Override
            public Map<String, Object> listFlockByUuid(Integer uuid) {
                return null;
            }

            @Override
            public Map<String, Object> selectById(Integer flockId, Integer uuid, Integer currentPage, Integer pageSize) {
                return null;
            }

            @Override
            public void addItem(List<ItemVO> voList, Integer flockId, Integer uuid) {

            }


            @Override
            public Map<String, Object> flockCountReport(List<Integer> flockIds, Integer uuid, Integer itemId, Integer itemContentId, Integer type, Float lat, Float lon, Double num) {
                return null;
            }

            @Override
            public void removeFlockLesson(List<ItemVO> voList, Integer flockId) {
            }

            @Override
            public Map<String, Object> invitePage(Integer uuid, Integer flockId) {
                return null;
            }

            @Override
            public List flockStatistic(List<FlockRecordVo> list1, Integer flockId, String startTimes, String endTimes, Integer pageNumber, Integer pageSize) {
                return null;
            }

        };
    }
}
