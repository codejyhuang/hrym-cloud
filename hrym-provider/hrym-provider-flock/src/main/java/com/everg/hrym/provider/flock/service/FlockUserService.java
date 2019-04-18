package com.everg.hrym.provider.flock.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.everg.hrym.api.flock.entity.Flock;
import com.everg.hrym.api.flock.entity.FlockItem;
import com.everg.hrym.api.flock.entity.FlockItemUserCount;
import com.everg.hrym.api.flock.entity.FlockUser;
import com.everg.hrym.api.lesson.facade.LessonFacade;
import com.everg.hrym.api.lesson.facade.TaskFacade;
import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.support.vo.ItemVO;
import com.everg.hrym.provider.flock.exception.FlockResultCode;
import com.everg.hrym.provider.flock.mapper.FlockUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FlockUserService {

    @Autowired
    private FlockUserMapper mapper;


    @Autowired
    private FlockService flockService;

    @Autowired
    private FlockItemUserCountService flockItemUserCountService;

    @Autowired
    private FlockItemService flockItemService;


    @Autowired
    private LessonFacade lessonFacade;

    @Autowired
    private TaskFacade taskFacade;

    /**
     * 群成员 列表
     *
     * @param flockId
     * @param uuid
     * @param currentPage
     * @param pageSize
     * @param keywords
     * @return
     */
    public Map<String, Object> flockUserList(Integer flockId, Integer uuid, Integer currentPage,
                                             Integer pageSize, String keywords) {
        Map<String, Object> map = new HashMap<>();
        List<FlockUser> adminList = mapper.queryByFlockId(flockId, FlockUser.ROLE_ADMIN, null);
        PageHelper.startPage(currentPage, pageSize);
        List<FlockUser> flockUserList = mapper.queryByFlockId(flockId, FlockUser.ROLE_MEMBER, keywords);
        PageInfo pageInfo = new PageInfo(flockUserList);
        //获取群主的用户信息
        FlockUser createUser = adminList.get(0);
        map.put("flockMember", flockUserList);
        map.put("hasNextPage", pageInfo.isHasNextPage());
        map.put("totalPages", pageInfo.getPages());
        map.put("total", pageInfo.getTotal());
        map.put("createUser", createUser);
        map.put("isAdmin", createUser.getUuid().equals(uuid) ? 1 : 0);
        return map;
    }

    @Transactional
    @LcnTransaction
    public synchronized void joinFlock(Integer flockId, Integer uuid) {
        FlockUser fUser = mapper.queryCountByFlockIdAndUuid(flockId, uuid);
        if (fUser != null) {
            throw new HrymException(FlockResultCode.ERROR1);
        }
        Flock flock = flockService.queryByPrimaryKey(flockId);
        if (flock != null && Flock.DISSOLVE_STATE.equals(flock.getState())) {
            throw new HrymException(FlockResultCode.ERROR2);
        }
        FlockUser flockUser = new FlockUser();
        flockUser.setUuid(uuid);
        flockUser.setType(FlockUser.ROLE_MEMBER);
        flockUser.setFlockId(flockId);
        flockUser.setCreateTime(DateUtil.currentSecond());
        flockUser.setOrder(0);
        mapper.insert(flockUser);
        //更新共修群 字段信息  同修人数
        flockService.updateFlockJoinNum(flockId, 1);
        //查询加入群的所有功课
        List<FlockItem> flockItemList = flockItemService.queryByFlockId(flockId);
        if (flockItemList.size() != 0) {
            for (FlockItem flockItem : flockItemList) {
                FlockItemUserCount flockItemUserCount = flockItemUserCountService.queryByFlockIdAndItemIdAndItemContentIdAndTypeAndUuid(flockId, flockItem.getItemId(),
                        flockItem.getItemContentId(), flockItem.getType(), uuid);
                //如果用户退出过该群  无需添加数据    没有则加入
                if (flockItemUserCount == null) {
                    //维护  功课用户的关系
                    flockItemUserCount = new FlockItemUserCount();
                    flockItemUserCount.setItemId(flockItem.getItemId());
                    flockItemUserCount.setFlockId(flockId);
                    flockItemUserCount.setItemContentId(flockItem.getItemContentId());
                    flockItemUserCount.setUuid(uuid);
                    flockItemUserCount.setTotal(new Double(0));
                    flockItemUserCount.setType(flockItem.getType());
                    flockItemUserCountService.insert(flockItemUserCount);
                }
            }
        }

        lessonFacade.updateOnlineNum(getItemVOLoist(flockItemList, 1));


        taskFacade.preserveTask(getItemVOLoist(flockItemList, 1), uuid);

    }


    /**
     * 删除群成员
     *
     * @param userIds 删除用户id 集合
     * @param flockId 群id
     * @return
     */
    @Transactional
    @LcnTransaction
    public void removeFlockUser(List<Integer> userIds, Integer uuid, Integer flockId) {
        if (userIds.size() != 0) {
            mapper.batchRemoveFlockUser(flockId, userIds);
            //更新共修群 字段信息  同修人数
            flockService.updateFlockJoinNum(flockId, -userIds.size());
            List<FlockItem> flockItemList = flockItemService.queryByFlockId(flockId);
            lessonFacade.updateOnlineNum(getItemVOLoist(flockItemList, -userIds.size()));
        }
    }


    /**
     * 退出共修群
     *
     * @param flockId 群id
     * @param uuid    用户id
     */
    @Transactional
    @LcnTransaction
    public void dropFlock(Integer flockId, Integer uuid) {
        mapper.batchRemoveFlockUser(flockId, Arrays.asList(uuid));
        //更新共修群 字段信息  同修人数
        flockService.updateFlockJoinNum(flockId, -1);
        //修改功课的 online_num
        List<FlockItem> flockItemList = flockItemService.queryByFlockId(flockId);

        lessonFacade.updateOnlineNum(getItemVOLoist(flockItemList, 1));
    }

    /**
     * 解散共修群
     *
     * @param flockId 群id
     * @param uuid    用户id
     */
    @Transactional
    @LcnTransaction
    public void dissolveFlock(Integer flockId, Integer uuid) {
        List<FlockUser> flockUserList = mapper.queryByFlockId(flockId, FlockUser.ROLE_MEMBER, null);
        if (flockUserList.size() != 0) {
            //抛异常
            return;
        }
        mapper.batchRemoveFlockUser(flockId, Arrays.asList(uuid));
        flockService.logicDeleteFlock(flockId, Flock.DISSOLVE_STATE);
        //修改功课的 online_num
        List<FlockItem> flockItemList = flockItemService.queryByFlockId(flockId);

        lessonFacade.updateOnlineNum(getItemVOLoist(flockItemList, 1));
    }


    public List<Integer> queryUuidListByFlockId(Integer id) {
        return mapper.queryUuidListByFlockId(id);
    }

    public Integer queryCountByFlockId(Integer id) {
        return mapper.queryCountByFlockId(id);
    }

    public void updateFlockUserOrder(Integer flockId, Integer order, Integer uUid) {
        mapper.updateFlockUserOrder(flockId, order, uUid);
    }

    public void insert(FlockUser flockUser) {
        mapper.insert(flockUser);
    }

    public FlockItem queryByFlockIdAndUuid(Integer next, Integer uuid) {
        return mapper.queryByFlockIdAndUuid(next, uuid);
    }

    public List<ItemVO> getItemVOLoist(List<FlockItem> flockItemList, Integer count) {
        List<ItemVO> itemVOList = new ArrayList<>();
        for (FlockItem flockItem : flockItemList) {
            ItemVO itemVO = new ItemVO();
            itemVO.setCount(count);
            itemVO.setItemId(flockItem.getItemId());
            itemVO.setItemContentId(flockItem.getItemContentId());
            itemVO.setType(flockItem.getType());
            itemVOList.add(itemVO);
        }
        return itemVOList;
    }

    public void updateUuidOfFlockUser(Integer uuid, Integer oldUuid) {
        mapper.updateUuidOfFlockUser(uuid,oldUuid);
    }
}
