package com.tistory.aircook.mybatis.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MemoResponse implements Serializable {

    private Integer id;

    private String memo;

    private String writer;

    private String writeAt;

}
