package com.everg.hrym.provider.lesson.controller;

import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.common.support.vo.TaskVO;
import com.everg.hrym.provider.lesson.service.RecordService;
import com.everg.hrym.provider.lesson.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("provider/task")
public class TaskController {

    @Autowired
    private TaskService taskService;


    /**
     * 维护 task
     *
     * @param list 功课集合
     * @param uuid
     */
    @PostMapping("preserveTask")
     void preserveTask(@RequestBody List<ItemVO> list, @RequestParam("uuid") Integer uuid){
        taskService.preserveTask(list,uuid);
    }


    /**
     * 我的  task
     *
     * @param  uuid
     */
    @PostMapping("myTask")
    Map<String,Object> myTask(@RequestParam("uuid") Integer uuid){
        return taskService.myTask(uuid);
    }

    /**
     * 任务 详情 (共修)
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param taskId
     * @param uuid
     * */
    @PostMapping("selfRepairCard")
    Map<String,Object> selfRepairCard(@RequestParam("itemId")Integer itemId,@RequestParam("itemContentId")Integer itemContentId,
                                      @RequestParam("type")Integer type,@RequestParam("taskId")Integer taskId,@RequestParam("uuid")Integer uuid){
        return taskService.selfRepairCard(itemId,itemContentId,type,taskId,uuid);
    }

    @PostMapping("totalUserBackup")
    Map<String,Object> totalUserBackup(Integer userId){
        return taskService.totalUserBackup(userId);
    }

    @GetMapping("exportExcel")
    Map<String, Object> exportExcel( @RequestParam("userId") Integer userId){
        return taskService.exportExcel(userId);
    }




    /**
     * 添加任务
     *
     * @param  itemId
     * @param  itemContentId
     * @param  type
     * @param  uuid
     */
    @PostMapping("addTask")
    void addTask(@RequestParam("uuid") Integer uuid ,@RequestParam("itemId") Integer itemId,@RequestParam("itemContentId") Integer itemContentId,@RequestParam("type") Integer type){
        taskService.addTask(uuid,itemId,itemContentId,type);
    }

    /**
     * 删除 任务
     *
     * @param  taskId
     * @param  type
     * @param  uuid
     */
    @PostMapping("removeTask")
    void removeTask(@RequestParam("taskId") Integer taskId, @RequestParam("type") Integer type,@RequestParam("uuid")Integer uuid){
        taskService.removeTask(taskId,type,uuid);
    }

    /**
     * 任务排序
     * @param taskList
     * @param uuid
     */
    @PostMapping("updateTaskOrder")
    void updateTaskOrder(@RequestBody List<TaskVO> taskList, @RequestParam("uuid")Integer uuid){
        taskService.updateTaskOrder(taskList,uuid);
    }


    /**
     * 任务排序
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     */
    @PostMapping("queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid")
    Integer queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(@RequestParam("itemId") Integer itemId,
                                                              @RequestParam("itemContentId") Integer itemContentId,
                                                              @RequestParam("type") Integer type,@RequestParam("uuid") Integer uuid){
        return taskService.queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(itemId,itemContentId,type,uuid);
    }


    ///////////////////////////////////////////////用户合并账号 更新数据///////////////////////////////////

    @PostMapping("mergeAccount")
    void updateUuidOfTask(@RequestParam("uuid") Integer uuid, @RequestParam("oldUuid") Integer oldUuid){
        taskService.mergeAccount(uuid,oldUuid);

    }

}
