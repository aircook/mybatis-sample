package com.tistory.aircook.mybatis.domain;

import lombok.Data;

@Data
public class MemoRequest {

    private Integer id;

    private String memo;

    private String writer;

}
