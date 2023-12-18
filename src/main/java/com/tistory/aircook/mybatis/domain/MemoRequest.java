package com.tistory.aircook.mybatis.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoRequest {

    private Integer id;

    private String memo;

    private String writer;

}
