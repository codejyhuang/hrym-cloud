package com.everg.hrym.api.lesson.fallback;

import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.common.support.vo.TaskVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by hong on 2019/3/22.
 */
@Component
public class TaskFacadeFallbackFactory implements FallbackFactory<TaskFacade> {

    @Override
    public TaskFacade create(Throwable throwable) {
        return new TaskFacade() {


            @Override
            public void preserveTask(List<ItemVO> list, Integer uuid) {

            }

            @Override
            public Map<String, Object> myTask(Integer uuid) {
                return null;
            }

            @Override
            public Map<String, Object> selfRepairCard(Integer itemId, Integer itemContentId, Integer type, Integer taskId, Integer uuid) {
                return null;
            }


            @Override
            public Map<String, Object> totalUserBackup(Integer userId) {
                return null;
            }
    
            @Override
            public Map<String, Object> exportExcel(Integer userId) {
    
                return null;
            }

            @Override
            public String queryUnitByUuidAndItemIdAndItemContentIdAndType(Integer userId, Integer itemId, Integer itemContentId, Integer type) {
                return null;
            }

            @Override
            public void addTask(Integer uuid, Integer itemId, Integer itemContentId, Integer type) {

            }

            @Override
            public void removeTask(Integer taskId, Integer type,Integer uuid) {

            }

            @Override
            public void updateTaskOrder(List<TaskVO> taskList, Integer uuid) {

            }

            @Override
            public Integer queryTaskIdByItemIdAndItemContentIdAndTypeAndUuid(Integer itemId, Integer itemContentId, Integer type, Integer uuid) {
                return null;
            }

            @Override
            public void mergeAccount(Integer uuid, Integer oldUuid) {

            }


        };
    }
}
