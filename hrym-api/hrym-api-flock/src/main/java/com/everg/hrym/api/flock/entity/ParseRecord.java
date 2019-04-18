package com.everg.hrym.api.flock.entity;

import com.everg.hrym.api.user.entity.UserAccount;
import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ParseRecord extends BaseEntity {

    private Integer recordType;  //0:慧共修  1: 慧祈福  2 : 慧助念

    private Integer to; //被赞人id

    private Integer flockId;

    private Integer itemId;

    private Integer contentId;

    private Integer itemType;

    private String relationId; //点赞动态 ：record_id\n今日 ：yyyymmdd\n月：yyyymm\n年：yyyy\n累计点赞：累计表中id

    private Integer from; //点赞人id

    private Integer time; //时间

    private Integer relationType;




    private UserAccount fromUser;

    private String timeStr;

    public String getTimeStr() {
        return DateUtil.getTimeChaHour(
                DateUtil.formatIntDate(time), new Date());
    }
}

