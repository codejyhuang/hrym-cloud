package com.everg.hrym.common.support.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by hrym13 on 2018/11/11.
 */
@Setter
@Getter
public class FlockRecordVo implements Serializable {
    private Integer flockId;
    private Integer itemContentId;
    private Integer itemId;
    private Integer type;
    private Integer uuid;
    private String startTimes;
    private String endTimes;
    private Integer startTime;
    private Integer endTime;
    private String nickname;
    private String avatar;
    private Double num;
    private Integer createTime;
    private String createTimes;
    private List<FlockRecordVo> flockRecordVo;
    private long totalPages;
    private String numStr;      //多少遍
    private long total;         //多少条
    private long peopleNum;  //多少人
    private String itemName;      //功课名称
    private String versionName;   // 版本内容名称
    private String lessonName;
    private boolean isHasNextPage;
    private Integer currentPage;
    private String flockName;
    private Integer order;
    private String myNumPre;
    private String maxFlockNumPre;
    private String myAvatar;
    private String numPre = "0%";
    private double flockNum;
    
    private Boolean state = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlockRecordVo that = (FlockRecordVo) o;
        return Objects.equals(itemContentId, that.itemContentId) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemContentId, itemId, type);
    }
    
   public static Double sum(FlockRecordVo vo1,FlockRecordVo vo2){
        return vo1.getNum()+vo2.getNum();
   }
    
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
