package com.everg.hrym.api.pray.entity;

import com.everg.hrym.common.core.util.NumUtil;
import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;


//祈福  助念功课
@Setter
@Getter
public class AssistPrayItem extends BaseEntity {

    private Integer assistPrayId; //群id

    private Integer itemId; //功课id

    private Integer itemContentId; //内容id

    private String unit; //量词

    private Integer createId; //功课创建者

    private Integer order; //排序

    private Integer type; //功课类型

    private Double dayDoneNum; //今日总数

    private Double totalDoneNum; //累计总数

    private Double targetDoneNum; //目标值

    private Integer distinction;// 1:慧祈福  2:慧助念

    private Integer state; // 0 未删除   1:已删除

    private String itemName;      //功课名称
    private String versionName;   // 版本内容名称

    private String lessonName;
    private Integer src =0;
    private String dayDoneNumStr;

    private String dayNumStr;
    private String targetNumStr; //目标值

    private String totalNumStr; //每日
    private String timeStr;     // 2018年12月04日
    private String weeks;           //周
    private String info;

    private String percent; //目标百分比


    public String getPercent() {
        if (targetDoneNum!=null&&targetDoneNum!=0){
            String percent = NumUtil.getPercentForWechat(totalDoneNum, targetDoneNum);
            return percent.substring(0,percent.length()-1);
        }else {
            return "100";
        }
    }

    public String getDayNumStr() {
        return NumUtil.getTotalNumStr(dayDoneNum,"0")+ (StringUtils.isNotBlank(unit)?unit:"遍");
    }
    public String getTargetNumStr() {
        return NumUtil.getTotalNumStr(targetDoneNum,"0")+ (StringUtils.isNotBlank(unit)?unit:"遍");
    }

    public String getTotalNumStr() {
        return NumUtil.getTotalNumStr(totalDoneNum,"0")+ (StringUtils.isNotBlank(unit)?unit:"遍");
    }
}
