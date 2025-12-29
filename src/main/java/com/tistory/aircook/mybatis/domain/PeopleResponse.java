package com.tistory.aircook.mybatis.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeopleResponse {

    private Integer id;

    private String name;

    private String birth;

}
