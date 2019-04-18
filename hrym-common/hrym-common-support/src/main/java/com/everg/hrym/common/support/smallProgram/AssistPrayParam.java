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
public class AssistPrayParam extends BaseRequestParam {

    private AssistPrayData data;

    public AssistPrayData getData() {
        return data;
    }
    public void setData(AssistPrayData data) {
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
    public Integer getBeginTime(){
        return data.getBeginTime();
    }
    public String getOverTime(){
        return data.getOverTime();
    }
    public Integer getProgramType(){
        return data.getProgramType();
    }
    public Integer getOverMethod(){
        return data.getOverMethod();
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
    
    public Integer getYear() {
        return data.getYear();
    }

}

@Setter
@Getter
class AssistPrayData implements Serializable{

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

    private Integer year;

    private String overTime;

    private Integer overMethod;

    private Integer beginTime;

    private Integer programType; //1:慧祈福   2:慧助念
    
    private Integer type;
    
    private List<FlockRecordVo> flockList;
    
    
}

