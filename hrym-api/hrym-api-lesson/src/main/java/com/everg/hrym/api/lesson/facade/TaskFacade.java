package com.everg.hrym.api.lesson.facade;

import com.everg.hrym.api.lesson.configuration.LessonConfiguration;
import com.everg.hrym.api.lesson.fallback.TaskFacadeFallbackFactory;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.common.support.vo.TaskVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@FeignClient(name = "hrym-provider-lesson", configuration = LessonConfiguration.class,fallback = TaskFacadeFallbackFactory.class)
public interface TaskFacade {

    /**
     * 维护 task
     *
     * @param list 功课集合
     * @param uuid
     */
    @PostMapping("provider/task/preserveTask")
     void preserveTask(@RequestBody List<ItemVO> list, @RequestParam("uuid") Integer uuid);


    /**
     * 我的  task
     *
     * @param  uuid
     */
    @PostMapping("provider/task/myTask")
    Map<String,Object> myTask(@RequestParam("uuid") Integer uuid);

    /**
     * 任务 详情 (共修)
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param taskId
     * @param uuid
     * */
    @PostMapping("provider/task/selfRepairCard")
    Map<String,Object> selfRepairCard(@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                                      @RequestParam("type")Integer type,@RequestParam("taskId")Integer taskId,@RequestParam("uuid")Integer uuid);
    
    @PostMapping("provider/task/totalUserBackup")
    Map<String,Object> totalUserBackup(Integer userId);
    
    @GetMapping("provider/task/exportExcel")
    Map<String, Object> exportExcel( @RequestParam("userId") Integer userId);
    

    /**
     * 查询功课量词
     *
     * @param  itemId
     * @param  itemContentId
     * @param  type
     * @param  uuid
     */
    @PostMapping("provider/unit/queryUnit")
    String queryUnitByUuidAndItemIdAndItemContentIdAndType(@RequestParam("uuid") Integer uuid,
                                                           @RequestParam("itemId") Integer itemId,
                                                           @RequestParam("itemContentId") Integer itemContentId,
                                                           @RequestParam("type") Integer type);

    /**
     * 添加任务
     *
     * @param  itemId
     * @param  itemContentId
     * @param  type
     * @param  uuid
     */
    @PostMapping("provider/task/addTask")
    void addTask(@RequestParam("uuid") Integer uuid ,@RequestParam("itemId") Integer itemId,@RequestParam("itemContentId") Integer itemContentId,@RequestParam("type") Integer type);

    /**
     * 删除 任务
     *
     * @param  taskId
     * @param  type
     * @param  uuid
     */
    @PostMapping("provider/task/removeTask")
    void removeTask(@RequestParam("taskId") Integer taskId, @RequestParam("type") Integer type,@RequestParam("uuid")Integer uuid);

    /**
     * 任务排序
     * @param taskList
     * @param uuid
     */
    @PostMapping("provider/task/updateTaskOrder")
    void updateTaskOrder(@RequestBody List<TaskVO> taskList, @RequestParam("uuid")Integer uuid);


    /**
     * 任务排序
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     */
    @PostMapping("provider/task/queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid")
    Integer queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(@RequestParam("itemId") Integer itemId,
                                                              @RequestParam("itemContentId") Integer itemContentId,
                                                              @RequestParam("type") Integer type,@RequestParam("uuid") Integer uuid);


    ///////////////////////////////////////////////用户合并账号 更新数据///////////////////////////////////

    @PostMapping("provider/task/mergeAccount")
    void mergeAccount(@RequestParam("uuid") Integer uuid, @RequestParam("oldUuid") Integer oldUuid);


}
