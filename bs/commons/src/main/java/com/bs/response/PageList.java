package com.bs.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class PageList<T>  implements Serializable {

    @ApiModelProperty(value = "总行数")
    private Long totalCount;
    @ApiModelProperty(value = "数据")
    private T data;

    public PageList(Long totalCount, T data) {
        if(totalCount == null){
            this.totalCount = 0L;
        }else {
            this.totalCount = totalCount;
        }
        this.data = data;
    }

    public PageList(Integer totalCount, T data) {
        if(totalCount == null){
            this.totalCount = 0L;
        }else {
            this.totalCount = totalCount.longValue();
        }
        this.data = data;
    }

}
