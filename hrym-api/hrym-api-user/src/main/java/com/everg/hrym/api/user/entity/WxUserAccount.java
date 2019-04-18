package com.everg.hrym.api.user.entity;

import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WxUserAccount extends BaseEntity {

    private String openid;

    private String nickname;

    private String avatar;

    private Integer createTime;

    private Integer updateTime;

    private Integer sex;

}