package com.everg.hrym.api.flock.facade;

import com.everg.hrym.api.flock.configuration.FlockConfiguration;
import com.everg.hrym.api.flock.fallback.FlockUserFacadeFallbackFactory;
import com.everg.hrym.common.support.vo.ItemVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-flock", configuration = FlockConfiguration.class,fallbackFactory = FlockUserFacadeFallbackFactory.class)
public interface FlockUserFacade {


    /**
     * 群成员 列表
     * @param flockId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param keywords 搜索关键字
     * @return
     */
    @PostMapping("provider/flockUser/flockUserList")
    Map<String,Object> flockUserList(@RequestParam("flockId")Integer flockId,@RequestParam("uuid")Integer uuid ,@RequestParam("currentPage")Integer currentPage,
                                     @RequestParam("pageSize")Integer pageSize,@RequestParam(value = "keywords",required = false)String keywords);

    /**
     * 加入共修群
     * @param flockId 群id
     * @param uuid 用户id
     * @return
     */
    @PostMapping("provider/flockUser/joinFlock")
    void joinFlock(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid);

    /**
     * 删除群成员
     *
     * @param userIds  删除用户id 集合
     * @param uuid  删除用户id
     * @param flockId  群id
     * @return
     */
    @PostMapping("provider/flockUser/removeFlockUser")
    void   removeFlockUser(@RequestBody List<Integer> userIds, @RequestParam("uuid") Integer uuid, @RequestParam("flockId") Integer flockId);


    /**
     * 退出共修群
     * @param flockId  群id
     * @param uuid 用户id
     */
    @PostMapping("provider/flockUser/dropFlock")
    void dropFlock(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid);

    /**
     * 解散共修群
     * @param flockId  群id
     * @param uuid 用户id
     */
    @PostMapping("provider/flockUser/dissolveFlock")
    void dissolveFlock(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid);

}

