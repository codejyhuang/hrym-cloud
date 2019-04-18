package com.everg.hrym.api.lesson.entity;

import com.everg.hrym.api.user.entity.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LessonCommon implements Serializable {

    private Integer itemId;             // 功课ID

    private Integer itemContentId;      // 功课内容ID

    private Integer type;

    private Integer onlineNum;

    private Integer userId;  //功课创建人uuid

    private String lessonName;

    private String versionName;         // 版本名称

    private Integer isAdd = 0;

    private String unit; //功课量词

    private String info;

    private String intro;

    private Integer privacy;   //功课隐私   1:公开    2: 隐私  3:公开课名  隐私动态

    private Integer createTime;

    private String createTimeStr;

    private UserAccount createUser;
}
