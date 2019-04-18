package com.everg.hrym.common.support.smallProgram;

import com.everg.hrym.common.support.base.BaseRequestParam;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created on 2018/11/08.
 */
@Setter
@Getter
public class QueryObjectParam extends BaseRequestParam {

    private QueryObjectDataParam data;

    public QueryObjectDataParam getData() {
        return data;
    }
    public Integer getPageSize() {
        return data.getPageSize();
    }

    public Integer getCurrentPage() {
        return data.getCurrentPage();
    }
    public Integer getType() {
        return data.getType();
    }
    public Integer getUuid() {
        return data.getUuid();
    }
    public Integer getId() {
        return data.getId();
    }
    public String getKeywords() {
        return data.getKeywords();
    }
    public Integer getProgramType() {
        return data.getProgramType();
    }
    public Integer getPageNumber() {
        return data.getPageNumber();
    }
}

@Setter
@Getter
class QueryObjectDataParam implements Serializable{

    private Integer pageSize;

    private Integer currentPage;

    private Integer programType;

    private String keywords;

    private Integer type;

    private Integer uuid;

    private Integer id;
    private Integer pageNumber;

    public String getKeywords() {
        return StringUtils.isNotEmpty(keywords)?keywords:null;
    }
    
}

