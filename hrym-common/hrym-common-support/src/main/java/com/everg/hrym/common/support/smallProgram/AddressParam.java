package com.everg.hrym.common.support.smallProgram;

import com.everg.hrym.common.support.base.BaseRequestParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by hrym13 on 2019/2/25.
 */
public class AddressParam extends BaseRequestParam {
    private AddressDataParam data;

    public AddressDataParam getData() {
        return data;
    }

    public Integer getId() {
        return data.getId();
    }

    public Integer getPopularizeId() {
        return data.getPopularizeId();
    }

    public Integer getIsDefault() {
        return data.getIsDefault();
    }

    public String getName() {
        return data.getName();
    }

    public String getPhone() {
        return data.getPhone();
    }

    public String getAddress() {
        return data.getAddress();
    }
}

@Setter
@Getter
class AddressDataParam implements Serializable {

    private Integer id;

    private Integer popularizeId;

    private String name;

    private String phone;

    private String address;

    private Integer isDefault; //0:否  1:是  默认地址

}
