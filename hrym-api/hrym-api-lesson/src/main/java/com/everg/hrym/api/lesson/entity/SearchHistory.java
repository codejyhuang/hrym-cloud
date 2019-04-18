package com.everg.hrym.api.lesson.entity;

import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchHistory extends BaseEntity {

    private Integer uuid;

    private String content;

    private Integer createTime;
}
