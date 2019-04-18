package com.everg.hrym.common.support.smallProgram;

import com.everg.hrym.common.support.base.BaseRequestParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by hrym13 on 2018/4/16.
 */
public class WxLoginParam extends BaseRequestParam implements Serializable {

    private WxLoginData data;

    public WxLoginData getData() {
        return data;
    }

    public void setData(WxLoginData data) {
        this.data = data;
    }

    public Integer getMeditationId() {
        return data.getMeditationId();
    }

    public String getOpenId() {
        return data.getOpenId();
    }

    public Integer getUserId() {
        return data.getUserId();
    }

    public String getGroupId() {
        return data.getGroupId();
    }

    public Integer getUpdateTime() {
        return data.getUpdateTime();
    }

    public Integer getCreatedTime() {
        return data.getCreatedTime();
    }

    public String getMeditationName() {
        return data.getMeditationName();
    }

    public String getMeditationIntro() {
        return data.getMeditationIntro();
    }

    public String getCodeUrl() {
        return data.getCodeUrl();
    }

    public String getMeditationUrl() {
        return data.getMeditationUrl();
    }

    public Integer getMeditationType() {
        return data.getMeditationType();
    }


    public String getSessionKey() {
        return data.getSessionKey();
    }

    public String getEncryptedData() {
        return data.getEncryptedData();
    }

    public String getIv() {
        return data.getIv();
    }


    public Integer getStatus() {
        return data.getStatus();
    }

    public Integer getProgramType() {
        return data.getProgramType();
    }

    public String getUserInfo() {
        return data.getUserInfo();
    }

    public String getOpenGId() {
        return data.getOpenGId();
    }

    public String getJscode() {
        return data.getJscode();
    }

    public String getScene() {
        return data.getScene();
    }

    public Integer getDistinction() {
        return data.getDistinction();
    }

    public Integer getUuid() {
        return data.getUuid();
    }


}

@Setter
@Getter
class WxLoginData implements Serializable {

    private Integer meditationId;

    private Integer userId;

    private String groupId;                    //群ID

    private Integer createdTime;

    private String meditationName;      //群名称

    private String meditationIntro;    //群简介

    private String codeUrl;             //群二维码

    private String meditationUrl;       //群头像

    private Integer meditationType;     //群类型

    private String openId;

    private Integer updateTime;

    private String sessionKey;

    private String encryptedData;

    private String iv;

    private Integer status;

    private String userInfo;

    private String openGId;

    private String jscode;

    private String unionId;

    private Integer uuid;

    private String scene;

    private Integer distinction;

    private Integer programType;

}
