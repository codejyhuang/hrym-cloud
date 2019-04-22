package com.everg.hrym.api.pray.entity;

import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//祈福 助念实体类
@Setter
@Getter
public class AssistPray extends BaseEntity {

    public static final Integer DEFAULT_PRIVACY_STATE = 2;  //默认私密状态
    public static final Integer PUBLIC_PRIVACY_STATE = 1;  //公开状态

    public static final Integer NORMAL_STATE = 1;
    public static final Integer DISSOLVE_STATE = 0;


    private String name; //群名称

    private String intro; //群简介

    private Integer privacy = DEFAULT_PRIVACY_STATE; //是否公开  默认私密

    private String dedicationVerses; //群回响文

    private Integer createTime; //创建时间

    private Double dayDoneNum;  //今日统计报数

    private Double totalDoneNum; //群总报数

    private Integer itemJoinNum; //共修人数

    private Integer distinction; // 1:慧祈福   2:会助念

    private Integer itemNum; //功课数

    private Integer beginTime;  //开始时间

    private Integer overTime; //结束时间

    private Integer overMethod;

    private Integer meritId; //住持id

    private String avatarUrl; //群头像

    private UserAccount createUser; //群主

    private List<AssistPrayItem> assistPrayItems;


    private String viewStr;  //创建于

    private String lessonStr; //

    private String dayNumStr; //1023万2934遍

    private String totalNumStr; //12亿2882万2832遍

    private Integer state;      //0:已结束   1:进行中

    private String avatar;

    private String nickName;
    private Integer uuid;
    private String lessonName;
    private String unit;        //量词
    private Double targetDoneNum;       //任务目标

}
