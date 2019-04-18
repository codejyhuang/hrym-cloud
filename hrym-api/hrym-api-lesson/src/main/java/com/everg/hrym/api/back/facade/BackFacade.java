package com.everg.hrym.api.back.facade;

import com.everg.hrym.api.back.configuration.BackConfiguration;
import com.everg.hrym.api.back.fallback.BackFacadeFallbackFactory;
import com.everg.hrym.common.support.smallProgram.FlockUserBackParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-lesson", configuration = BackConfiguration.class,fallbackFactory = BackFacadeFallbackFactory.class)
public interface BackFacade {
    /**
     * 粘贴上次回向
     *
     * @param itemId
     * @return
     */
    @PostMapping(value = "provider/back/lastSpecialBack")
    Map<String, Object> lastSpecialBack(@RequestParam("itemType") Integer itemType,
                                        @RequestParam("itemId") Integer itemId,
                                        @RequestParam("uuid") Integer uuid,
                                        @RequestParam("contentId") Integer contentId);

    @PostMapping(value = "provider/back/insertSpecialBack")
    void insertSpecialBack(@RequestBody FlockUserBackParam param);

    @PostMapping(value = "provider/back/updateSpecialBack")
    void updateSpecialBack(@RequestParam("id") Integer id, @RequestParam("info") String info);

    @PostMapping(value = "provider/back/querySpecialBackDetails")
    Map<String, Object> querySpecialBackDetails(@RequestParam("id") Integer id);

    @PostMapping(value = "provider/back/queryCountDirection")
    Map<String, Object> queryCountDirection(@RequestBody FlockUserBackParam param);

    @PostMapping(value = "provider/back/deleteSpecialBack")
    void deleteSpecialBack(@RequestBody FlockUserBackParam param);

    @PostMapping(value = "provider/back/queryDedicationVersesList")
    Map<String, Object> queryDedicationVersesList(@RequestParam("uuid") Integer uuid, @RequestParam("type") Integer type,
                                                  @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize,
                                                  @RequestParam("flockId") Integer flockId, @RequestParam("year") Integer year);

    @PostMapping(value = "provider/back/queryDedicationVerses")
    Map<String, Object> queryDedicationVerses(@RequestParam("id") Integer id);

    @PostMapping(value = "provider/back/queryDedicationVersesRecordList")
    Map<String, Object> queryDedicationVersesRecordList(@RequestParam("timeStr") String timeStr,
                                                        @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam("flockId") Integer flockId, @RequestParam("ymd") String ymd);
}
