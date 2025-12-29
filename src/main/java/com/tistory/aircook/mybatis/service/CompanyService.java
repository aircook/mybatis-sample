package com.tistory.aircook.mybatis.service;

import com.tistory.aircook.mybatis.domain.CompanyRequest;
import com.tistory.aircook.mybatis.domain.CompanyResponse;
import com.tistory.aircook.mybatis.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyResponse> findCompanyAll() {
        return companyRepository.findCompanyAll();
    }

    public CompanyResponse findCompanyById(Integer id) {
        return companyRepository.findCompanyById(id);
    }

    public Integer insertCompany(CompanyRequest companyRequest) {
        return companyRepository.insertCompany(companyRequest);
    }

    public Integer updateCompany(CompanyRequest companyRequest) {
        return companyRepository.updateCompany(companyRequest);
    }

    public Integer deleteCompanyById(Integer id) {
        return companyRepository.deleteCompanyById(id);
    }

}



