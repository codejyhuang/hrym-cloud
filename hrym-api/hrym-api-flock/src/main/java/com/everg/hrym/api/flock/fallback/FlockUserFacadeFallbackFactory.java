package com.everg.hrym.api.flock.fallback;

import com.everg.hrym.api.flock.facade.FlockUserFacade;
import com.everg.hrym.common.support.vo.ItemVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class FlockUserFacadeFallbackFactory implements FallbackFactory<FlockUserFacade> {

    @Override
    public FlockUserFacade create(Throwable throwable) {

        if(throwable !=null){

            System.out.println(throwable.getCause());
        }
        return new FlockUserFacade() {

            @Override
            public Map<String, Object> flockUserList(Integer flockId, Integer uuid, Integer currentPage, Integer pageSize, String keywords) {
                return null;
            }

            @Override
            public void joinFlock(Integer flockId, Integer uuid) {

            }

            @Override
            public void removeFlockUser(List<Integer> userIds, Integer uuid, Integer flockId) {

            }

            @Override
            public void dropFlock(Integer flockId, Integer uuid) {

            }

            @Override
            public void dissolveFlock(Integer flockId, Integer uuid) {

            }

        };
    }
}
