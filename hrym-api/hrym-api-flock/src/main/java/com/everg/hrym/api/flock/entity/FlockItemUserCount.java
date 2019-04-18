package com.everg.hrym.api.flock.entity;

import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlockItemUserCount extends BaseEntity {

    private Integer flockId;  //群id

    private Integer itemId; //功课id

    private Integer itemContentId; //内容id

    private Integer uuid; //用户id

    private Double total; //功课总报数

    private Integer type; //功课类型


    private String lessonName;

}
