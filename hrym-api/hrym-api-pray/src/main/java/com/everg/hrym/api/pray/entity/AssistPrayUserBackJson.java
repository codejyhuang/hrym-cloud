package com.everg.hrym.api.pray.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hrym13 on 2018/12/3.
 */
@Setter
@Getter
public class AssistPrayUserBackJson implements Serializable {
    
    private List<AssistPrayItem> list;
    private Integer uuid;
    
    private String avatarUrl;
    private String name;
    private String nickname;
    private String ymd;
    private String time;
    private Integer assistPrayId;
    private Integer itemJoinNum;
    private String weeks;
    private String timeStr;
    private String info;
    
}
