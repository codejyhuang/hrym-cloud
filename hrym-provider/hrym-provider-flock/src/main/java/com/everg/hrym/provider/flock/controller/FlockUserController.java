package com.everg.hrym.provider.flock.controller;

import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.flock.service.FlockUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/flockUser")
public class FlockUserController {

    @Autowired
    private FlockUserService flockUserService;

    /**
     * 群成员 列表
     *
     * @param flockId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param keywords    搜索关键字
     * @return
     */
    @PostMapping("flockUserList")
    Map<String, Object> flockUserList(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid, @RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "keywords", required = false) String keywords) {

        return flockUserService.flockUserList(flockId, uuid, currentPage, pageSize, keywords);
    }

    /**
     * 加入共修群
     *
     * @param flockId 群id
     * @param uuid    用户id
     * @return
     */
    @PostMapping("joinFlock")
    void joinFlock(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid) {
         flockUserService.joinFlock(flockId, uuid);
    }

    /**
     * 删除群成员
     *
     * @param userIds 删除用户id 集合
     * @param uuid    删除用户id
     * @param flockId 群id
     * @return
     */
    @PostMapping("removeFlockUser")
    void removeFlockUser(@RequestBody List<Integer> userIds, @RequestParam("uuid") Integer uuid, @RequestParam("flockId") Integer flockId) {
        flockUserService.removeFlockUser(userIds, uuid, flockId);
    }


    /**
     * 退出共修群
     *
     * @param flockId 群id
     * @param uuid    用户id
     */
    @PostMapping("dropFlock")
    void dropFlock(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid) {
         flockUserService.dropFlock(flockId, uuid);
    }

    /**
     * 解散共修群
     *
     * @param flockId 群id
     * @param uuid    用户id
     */
    @PostMapping("dissolveFlock")
    void dissolveFlock(@RequestParam("flockId") Integer flockId, @RequestParam("uuid") Integer uuid) {
         flockUserService.dissolveFlock(flockId, uuid);
    }
}
