package com.everg.hrym.common.support.smallProgram;

import com.everg.hrym.common.support.base.BaseRequestParam;
import com.everg.hrym.common.support.vo.FlockRecordVo;
import com.everg.hrym.common.support.vo.ItemVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2018/11/08.
 */
public class WechatFlockParam extends BaseRequestParam {

    private WechatFlockData data;

    public WechatFlockData getData() {
        return data;
    }
    public void setData(WechatFlockData data) {
        this.data = data;
    }

    public String getName() {
        return data.getName();
    }
    public Integer getId() {
        return data.getId();
    }

    public String getIntro() {
        return data.getIntro();
    }

    public Integer getPrivacy() {
        return data.getPrivacy();
    }

    public String getDedicationVerses() {
        return data.getDedicationVerses();
    }

    public Integer getCreateTime() {
        return data.getCreateTime();
    }

    public Float getDayDoneNum() {
        return data.getDayDoneNum();
    }

    public Float getTotalDoneNum() {
        return data.getTotalDoneNum();
    }

    public Integer getItemJoinNum() {
        return data.getItemJoinNum();
    }

    public Integer getItemNum() {
        return data.getItemNum();
    }

    public Integer getUUid() {
        return data.getUuid();
    }

    public List<ItemVO> getVoList(){return data.getVoList();}

    public List<Integer> getUserIds(){
        return data.getUserIds();}

    public String getKeywords() {
        return StringUtils.isNotEmpty(data.getKeywords())?data.getKeywords():null;
    }

    public Integer getCurrentPage(){
        return data.getCurrentPage();
    }
    
    public Integer getPageSize(){
        return data.getPageSize();
    }
    
    public List<FlockRecordVo> getFlockList() {
        return data.getFlockList();
    }
    
    public Integer getType() {
        return data.getType();
    }

    public Double getTotalFlockNum() {
        return data.getTotalFlockNum();
    }

    public Double getTotalMyNum() {
        return data.getTotalMyNum();
    }

    
}

@Setter
@Getter
class WechatFlockData implements Serializable{

    private Integer id; //共修群id

    private String name; //群名称

    private String intro; //群简介

    private Integer privacy; //是否公开  默认私密

    private String dedicationVerses; //群回响文

    private Integer createTime; //创建时间

    private Float dayDoneNum;  //今日统计报数

    private Float totalDoneNum; //群总报数

    private Integer itemJoinNum; //共修人数

    private Integer itemNum; //功课数

    private Integer uuid; //群主id

    private List<ItemVO> voList;
    
    private List<Integer> userIds;
    
    private String keywords;

    private Integer currentPage;

    private Integer pageSize;
    
    private Integer order;
    
    private List<FlockRecordVo> flockList;
    
    private Integer type;

    private Double totalFlockNum;
    private Double totalMyNum;
    
}

