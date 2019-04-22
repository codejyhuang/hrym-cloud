package com.everg.hrym.api.pray.entity;

import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssistPrayUser extends BaseEntity {

    public static final Integer ROLE_ADMIN=0;
    public static final Integer ROLE_MEMBER=1;

    private Integer assistPrayId; //共修群id

    private Integer uuid; //用户id

    private Integer type = ROLE_MEMBER; // 0:群管理员  1:群成员

    private Integer order;

    private String avatar; //用户头像url

    private Integer distinction; //1:慧祈福  2:慧助念

    private String nickName;

    private Integer src = 0;
    
    private Integer createTime;
}
