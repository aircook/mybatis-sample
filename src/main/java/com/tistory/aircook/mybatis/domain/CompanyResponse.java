package com.tistory.aircook.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {
    
    private Integer id;

    /**
     * 회사명
     */
    private String name;

    /**
     * 주요 업종
     */
    private String industry;

    /**
     * 사원 수
     */
    private Integer employeeCount;

    /**
     * 창립 연도
     */
    private Integer foundedYear;

}



