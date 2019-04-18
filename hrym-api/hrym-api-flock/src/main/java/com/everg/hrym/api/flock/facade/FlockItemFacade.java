package com.everg.hrym.api.flock.facade;

import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.ParseRecord;
import com.everg.hrym.api.flock.fallback.FlockItemFacadeFallbackFactory;
import com.everg.hrym.common.support.vo.ItemVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-flock",fallbackFactory = FlockItemFacadeFallbackFactory.class)
public interface FlockItemFacade {

    @GetMapping("provider/flockItem/exportFlockItem")
    Map<String, Object> exportFlockItem(@RequestParam("flockId") Integer flockId);

    /**
     * 获取上报记录动态数据
     *
     * @param currentPage
     * @param pageSize
     * @param flockId
     * @param itemId
     * @param itemContentId
     * @param type
     * @param timeType
     * @param uuid
     * @return
     */
    @PostMapping("provider/flockItem/flockMessageRecord")
    Map<String,Object> flockMessageRecord(@RequestParam("currentPage")Integer currentPage,@RequestParam("pageSize")Integer pageSize,
                                          @RequestParam("flockId")Integer flockId,@RequestParam("itemId")Integer itemId,
                                          @RequestParam("itemConetentId")Integer itemContentId,@RequestParam("type")Integer type,
                                          @RequestParam("timeType")Integer timeType,@RequestParam("uuid")Integer uuid);

    /**
     * 删除 群 record 记录
     *
     * @param recordId
     * @param year
     * @return
     */
    @PostMapping("provider/flockRecord/removeFlockRecord")
    void removeFlockRecord(@RequestParam("recordId") Integer recordId, @RequestParam("year") Integer year);

    /**
     * 动态点赞
     *
     * @param id
     * @param year
     * @param uuid
     * @param type
     * @return
     */
    @PostMapping("provider/flockRecord/parseRecord")
    void parseRecord(@RequestParam("id")Integer id,@RequestParam("year") Integer year,@RequestParam("uuid") Integer uuid,@RequestParam("type")Integer type);


    /**
     * 动态点赞
     *
     * @param recordId
     * @return
     */
    @PostMapping("provider/flockRecord/queryLikeMember")
    List<ParseRecord> queryLikeMember(@RequestParam("recordId") Integer recordId);
    

    @PostMapping("/provider/flockItem/flockMessageBack")
    Map<String,Object> flockMessageBack(@RequestParam("flockId") Integer flockId,@RequestParam("uuid")Integer uuid);

    /**
     * 查询共修群功课
     * @param flockId
     * @return
     */
    @PostMapping("provider/flockItem/queryFlockItemList")
    List<FlockItem> queryFlockItemList(@RequestParam("flockId") Integer flockId);

    /**
     * 群添加功课  功课列表
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param type
     * @param keywords
     * @param flockId
     * @return
     */
    @PostMapping("provider/flockItem/lessonList")
    Map<String,Object> lessonList(@RequestParam("uuid")Integer uuid ,@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("type") Integer type, @RequestParam(value = "keywords",required = false) String keywords,
                                  @RequestParam("flockId") Integer flockId);
}
