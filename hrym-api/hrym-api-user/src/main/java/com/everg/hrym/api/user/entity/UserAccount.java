package com.everg.hrym.api.user.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserAccount  implements Serializable {

    private Integer uuid;

    private String socialUid;

    private String name;

    private String nickname;

    private String mobile;

    private String email;

    private String password;

    private Integer sex;

    private Integer source;

    private String avatar;

    private Integer createdTime;

    private Integer updatedTime;

    private Integer status;

    private Integer ymd;

    private String deviceId;

    private String identityCardNo;

    private String identityCardUrl1;

    private String identityCardUrl2;

    private String identityCardUrl3;

    private Integer identityAuthState;

    private Integer phoneAuthState;

    private String profile;

    private String realName;

    private Integer recentTime;

    private String address;

    private String academicSystem;

    private String systematicStudyYears;

    private String userTatus;

    private Integer wxUuid;

    private String openId;

    private String province;

}