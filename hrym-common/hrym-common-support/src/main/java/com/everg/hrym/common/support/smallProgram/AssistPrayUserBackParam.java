package com.everg.hrym.common.support.smallProgram;

import com.everg.hrym.common.support.base.BaseRequestParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by hrym13 on 2018/11/28.
 */
public class AssistPrayUserBackParam extends BaseRequestParam {
    
    private AssistPrayUserBackData data;
    
    public AssistPrayUserBackData getData() {
        return data;
    }
    
    public void setData(AssistPrayUserBackData data) {
        this.data = data;
    }
    
    public Integer getId() {
        return data.getId();
    }
    
    public Integer getAssistPrayId() {
        return data.getAssistPrayId();
    }
    
    public Integer getItemId() {
        return data.getItemId();
    }
    
    public Integer getContentId() {
        return data.getContentId();
    }
    
    public Integer getType() {
        return data.getType();
    }
    
    public Integer getTime() {
        return data.getTime();
    }
    
    public Integer getFlag() {
        return data.getFlag();
    }
    
    public String getInfo() {
        return data.getInfo();
    }
    
    public Integer getUuid() {
        return data.getUuid();
    }
    
    public Integer getItemType() {
        return data.getItemType();
    }
    
    public Integer getCurrentPage() {
        return data.getCurrentPage();
    }
    
    public Integer getPageSize() {
        return data.getPageSize();
    }
    
    public Integer getRecordId() {
        return data.getRecordId();
    }
    
    public String getYmd() {
        return data.getYmd();
    }
    public Double getNum() {
        return data.getNum();
    }
    
    public String getTimeStr() {
        return data.getTimeStr();
    }
    
    public Integer getDistinction() {
        return data.getDistinction();
    }
    
}

@Setter
@Getter
class AssistPrayUserBackData implements Serializable {
    
    private Integer id;
    private Integer assistPrayId;
    private Integer itemId;
    private Integer contentId;             // '版本id',
    private Integer itemType;            //'功课类型\n0:平台\n1:自建\n2:经书',
    private Integer type;                 //'0:群回向\n1:特别回向',
    private Integer time;                  // '回向时间',
    private Integer flag;              //'是否可修改\n0:可以修改\n1:不可以',
    private String info;                  //'回向文',
    private Integer uuid;                 //'0:系统统一回向\n其他：uuid',
    private Integer currentPage;
    private Integer pageSize;
    private Integer recordId;
    private String ymd;
    private Double num; //上报数
    private String timeStr;
    private Integer distinction;        //'0：慧共修，1：慧祈福；2：慧助念',
    
}
