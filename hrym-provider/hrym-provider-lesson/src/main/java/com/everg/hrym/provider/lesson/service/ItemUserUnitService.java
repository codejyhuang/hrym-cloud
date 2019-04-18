package com.everg.hrym.provider.lesson.service;


import com.everg.hrym.api.lesson.entity.ItemUserUnit;
import com.everg.hrym.provider.lesson.mapper.ItemUserUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Service
public class ItemUserUnitService {

    @Autowired
    private ItemUserUnitMapper mapper;


    public ItemUserUnit queryItemUserUnit(Integer userId, Integer itemId, Integer itemContentId, Integer type) {
        return mapper.queryItemUserUnit(userId, itemId, itemContentId, type);
    }

    public void updateItemUserUnitById(ItemUserUnit itemUserUnit) {
        mapper.updateItemUserUnitById(itemUserUnit);
    }

    public void insert(ItemUserUnit itemUserUnit) {
        mapper.insert(itemUserUnit);
    }

    /**
     * 查询功课量词
     *
     * @param itemId
     * @param itemContentId
     * @param type
     * @param uuid
     */
    public String queryUnitByUuidAndItemIdAndItemContentIdAndType(Integer uuid,
                                                                  Integer itemId,
                                                                  Integer itemContentId,
                                                                  Integer type) {
        return mapper.queryUnitByUuidAndItemIdAndItemContentIdAndType(uuid, itemId, itemContentId, type);
    }
}
