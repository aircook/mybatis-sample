package com.tistory.aircook.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoResponse implements Serializable {

    private Integer id;

    private String memo;

    private String writer;

    private String writeAt;

}
