package com.tistory.aircook.mybatis.domain;

import lombok.Data;

@Data
public class MemoResponse {

    private Integer id;

    private String memo;

    private String writer;

    private String writeAt;

}