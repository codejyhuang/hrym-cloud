package com.everg.hrym.api.pray.fallback;

import com.everg.hrym.api.pray.facade.PrayFacade;
import com.everg.hrym.common.support.vo.ItemVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class PrayFacadeFallbackFactory implements FallbackFactory<PrayFacade> {

    @Override
    public PrayFacade create(Throwable throwable) {
        return new PrayFacade() {


            @Override
            public Map<String, Object> createPray(List<ItemVO> voList, Integer uuid, String prayName, String dedicationVerses, Integer privacy, String intro, Integer programType, Integer overMethod, String overTime) {
                return null;
            }

            @Override
            public Map<String, Object> assistPrayDetails(Integer prayId, Integer uuid, Integer currentPage, Integer pageSize) {
                return null;
            }
        };
    }
}
