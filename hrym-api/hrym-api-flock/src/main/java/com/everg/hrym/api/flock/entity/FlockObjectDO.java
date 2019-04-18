package com.everg.hrym.api.flock.entity;

import com.everg.hrym.common.support.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hrym13 on 2019/3/14.
 */
@Setter
@Getter
public class FlockObjectDO extends BaseEntity {
    private double flockThisTotal = 0;
    private double flockLastTotal = 0;
    private double myThisTotal = 0;
    private double myLastTotal = 0;
    
}
