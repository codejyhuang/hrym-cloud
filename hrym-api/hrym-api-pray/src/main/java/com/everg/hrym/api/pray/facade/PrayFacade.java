package com.everg.hrym.api.pray.facade;

import com.everg.hrym.api.pray.configuration.PrayConfiguration;
import com.everg.hrym.api.pray.fallback.PrayFacadeFallbackFactory;
import com.everg.hrym.common.support.smallProgram.AssistPrayParam;
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
@FeignClient(name = "hrym-provider-pray", configuration = PrayConfiguration.class,fallbackFactory = PrayFacadeFallbackFactory.class)
public interface PrayFacade {


    /**
     * 创建 助念
     * @param voList
     * @return
     */
    @PostMapping("provider/pray/createPray")
    Map<String,Object> createPray(@RequestBody List<ItemVO> voList, @RequestParam("uuid")Integer uuid , @RequestParam("prayName")String prayName,
                                  @RequestParam(value = "dedicationVerses",required = false)String dedicationVerses,
                                  @RequestParam("privacy")Integer privacy, @RequestParam(value = "intro",required = false)String intro,
                                  @RequestParam("programType")Integer programType,@RequestParam("overMethod")Integer overMethod,@RequestParam(value = "overTime",required = false)String overTime);


    /**
     * 详情页
     * @param prayId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("provider/pray/assistPrayDetails")
    Map<String,Object> assistPrayDetails(@RequestParam("prayId") Integer prayId,@RequestParam("uuid") Integer uuid,
                                         @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize);
}

