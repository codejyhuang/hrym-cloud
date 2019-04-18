package com.everg.hrym.api.lesson.entity;

import com.everg.hrym.common.core.util.DateUtil;
import com.everg.hrym.common.core.util.NumUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

@Setter
@Getter
public class RecordCommon implements Serializable {

    private Integer recordId;

    private Integer taskId;

    private Integer userId;

    private Long reportNum;

    private String percent;

    private Integer reportTime;

    private Integer itemId;

    private Integer itemContentId;

    private String ymd;

    private Integer year;

    private Integer month;

    private String numStr;

    private String timeStr;

    private Integer recordMethod;
    
    private String reportTimeStr;

    private Integer recordStatus;
    
    private String lessonName;
    
    private Integer indexId;

    public String getNumStr() {
        return NumUtil.getTotalNumStr(new Double(reportNum==null?0:reportNum),"0");
    }

    public String getTimeStr() {
        return DateUtil.timestampToDates(reportTime,"yyyy年MM月dd日 HH:mm");
    }

    public String getReportTimeStr() {
        return StringUtils.isNotBlank(reportTime.toString())? DateUtil.timestampToDates(reportTime, DateUtil.TIME_PATTON_DEFAULT1): DateUtil.timestampToDates(DateUtil.currentSecond(), DateUtil.TIME_PATTON_DEFAULT1);
    }

    public String getTxtStyle(){
        return "";
    }
}
