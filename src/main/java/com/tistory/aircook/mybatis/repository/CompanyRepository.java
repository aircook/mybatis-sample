package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.domain.CompanyRequest;
import com.tistory.aircook.mybatis.domain.CompanyResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyRepository {

    List<CompanyResponse> findCompanyAll();

    CompanyResponse findCompanyById(Integer id);

    Integer insertCompany(CompanyRequest companyRequest);

    Integer updateCompany(CompanyRequest companyRequest);

    Integer deleteCompanyById(Integer id);

}



