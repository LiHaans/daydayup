package com.golaxy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vo {

    private String title;

    private Integer pageNum;

    private Integer level;

    private String content;

}
