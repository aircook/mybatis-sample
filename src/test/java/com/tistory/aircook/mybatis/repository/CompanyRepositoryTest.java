package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.domain.CompanyRequest;
import com.tistory.aircook.mybatis.domain.CompanyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * CompanyRepository 통합 테스트
 * @MybatisTest를 사용하여 실제 DB와 연동하여 MyBatis Mapper를 테스트합니다.
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("전체 회사 목록 조회 테스트")
    void findCompanyAll() {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .name("테스트 회사")
                .industry("테스트 업종")
                .employeeCount(100)
                .foundedYear(2020)
                .build();

        //when
        companyRepository.insertCompany(companyRequest);
        List<CompanyResponse> companyList = companyRepository.findCompanyAll();

        //then
        //data.sql에 초기 데이터 3건이 있으므로 3 이상이어야 함
        assertThat(companyList.size()).isGreaterThanOrEqualTo(3);

    }

    @Test
    @DisplayName("아이디로 회사 조회 테스트")
    void findCompanyById() {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .id(1)
                .name("테스트 회사")
                .industry("테스트 업종")
                .employeeCount(100)
                .foundedYear(2020)
                .build();

        //when
        companyRepository.insertCompany(companyRequest);
        CompanyResponse companyResponse1 = companyRepository.findCompanyById(companyRequest.getId());
        CompanyResponse companyResponse2 = companyRepository.findCompanyById(99999);

        //then
        assertThat(companyResponse1.getId()).isEqualTo(companyRequest.getId());
        assertThat(companyResponse1.getName()).isEqualTo(companyRequest.getName());
        assertThat(companyResponse1.getIndustry()).isEqualTo(companyRequest.getIndustry());
        assertThat(companyResponse1.getEmployeeCount()).isEqualTo(companyRequest.getEmployeeCount());
        assertThat(companyResponse1.getFoundedYear()).isEqualTo(companyRequest.getFoundedYear());
        assertNull(companyResponse2);

    }

    @Test
    @DisplayName("신규 회사 등록 테스트")
    void insertCompany() {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .name("신규 회사")
                .industry("신규 업종")
                .employeeCount(200)
                .foundedYear(2021)
                .build();

        //when
        companyRepository.insertCompany(companyRequest);
        CompanyResponse companyResponse = companyRepository.findCompanyById(companyRequest.getId());

        //then
        assertThat(companyResponse.getId()).isEqualTo(companyRequest.getId());
        assertThat(companyResponse.getName()).isEqualTo(companyRequest.getName());
        assertThat(companyResponse.getIndustry()).isEqualTo(companyRequest.getIndustry());
        assertThat(companyResponse.getEmployeeCount()).isEqualTo(companyRequest.getEmployeeCount());
        assertThat(companyResponse.getFoundedYear()).isEqualTo(companyRequest.getFoundedYear());

    }

    @Test
    @DisplayName("회사 정보 수정 테스트")
    void updateCompany() {

        //given
        CompanyRequest insertRequest = CompanyRequest.builder()
                .name("수정 전 회사")
                .industry("수정 전 업종")
                .employeeCount(100)
                .foundedYear(2020)
                .build();
        companyRepository.insertCompany(insertRequest);

        CompanyRequest updateRequest = CompanyRequest.builder()
                .id(insertRequest.getId())
                .name("수정 후 회사")
                .industry("수정 후 업종")
                .employeeCount(150)
                .foundedYear(2020)
                .build();

        //when
        companyRepository.updateCompany(updateRequest);
        CompanyResponse companyResponse = companyRepository.findCompanyById(updateRequest.getId());

        //then
        assertThat(companyResponse.getName()).isEqualTo(updateRequest.getName());
        assertThat(companyResponse.getIndustry()).isEqualTo(updateRequest.getIndustry());
        assertThat(companyResponse.getEmployeeCount()).isEqualTo(updateRequest.getEmployeeCount());
        assertEquals(updateRequest.getFoundedYear(), companyResponse.getFoundedYear());

    }

    @Test
    @DisplayName("회사 삭제 테스트")
    void deleteCompanyById() {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .name("삭제 대상 회사")
                .industry("삭제 대상 업종")
                .employeeCount(50)
                .foundedYear(2019)
                .build();
        companyRepository.insertCompany(companyRequest);
        Integer id = companyRequest.getId();

        //when
        companyRepository.deleteCompanyById(id);
        CompanyResponse companyResponse = companyRepository.findCompanyById(id);

        //then
        assertNull(companyResponse);

    }
}


