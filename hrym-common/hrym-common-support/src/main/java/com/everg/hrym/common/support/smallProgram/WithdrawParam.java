package com.everg.hrym.common.support.smallProgram;

import com.everg.hrym.common.support.base.BaseRequestParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by hrym13 on 2019/2/25.
 */
public class WithdrawParam extends BaseRequestParam {
    private WithdrawDataParam data;


    public WithdrawDataParam getData() {
        return data;
    }

    public Integer getId() {
        return data.getId();
    }

    public Integer getApplierId() {
        return data.getApplierId();
    }

    public String getAccountNum() {
        return data.getAccountNum();
    }

    public String getWithDrawAccount() {
        return data.getWithDrawAccount();
    }

    public Integer getAuditorId() {
        return data.getAuditorId();
    }

    public Integer getDistinction() {
        return data.getDistinction();
    }
    public Integer getAuditState() {
        return data.getAuditState();
    }

}

@Setter
@Getter
class WithdrawDataParam implements Serializable {
    private Integer id;

    private Integer applierId;

    private String accountNum;

    private String withDrawAccount;

    private Integer auditorId;

    private Integer distinction;

    private Integer auditState;


}
