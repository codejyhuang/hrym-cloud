package com.everg.hrym.api.pray.entity;

import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssistPrayItemUserCount extends BaseEntity {

    private Integer assistPrayId;  //群id

    private Integer itemId; //功课id

    private Integer itemContentId; //内容id

    private Integer uuid; //用户id

    private Double total; //功课总报数

    private Integer type; //功课类型

    private Integer distinction; //1: 慧祈福   2: 慧助念


    private String lessonName;

}
