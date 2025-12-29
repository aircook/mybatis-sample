package com.tistory.aircook.mybatis.service;

import com.tistory.aircook.mybatis.domain.CompanyRequest;
import com.tistory.aircook.mybatis.domain.CompanyResponse;
import com.tistory.aircook.mybatis.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * CompanyService 단위 테스트
 * Repository를 Mock으로 처리하여 Service 레이어의 비즈니스 로직을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("전체 회사 목록 조회 테스트")
    void findCompanyAll() {

        //given
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        companyResponseList.add(CompanyResponse.builder()
                .id(1)
                .name("삼성전자")
                .industry("전자제품 제조")
                .employeeCount(267937)
                .foundedYear(1969)
                .build());
        companyResponseList.add(CompanyResponse.builder()
                .id(2)
                .name("네이버")
                .industry("인터넷 서비스")
                .employeeCount(3500)
                .foundedYear(1999)
                .build());
        given(companyRepository.findCompanyAll()).willReturn(companyResponseList);

        //when
        List<CompanyResponse> result = companyService.findCompanyAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("삼성전자");
        assertThat(result.get(1).getName()).isEqualTo("네이버");

    }

    @Test
    @DisplayName("아이디로 회사 조회 테스트")
    void findCompanyById() {
        //given
        CompanyResponse companyResponse = CompanyResponse.builder()
                .id(1)
                .name("삼성전자")
                .industry("전자제품 제조")
                .employeeCount(267937)
                .foundedYear(1969)
                .build();
        given(companyRepository.findCompanyById(1)).willReturn(companyResponse);

        //when
        CompanyResponse result = companyService.findCompanyById(1);

        //then
        assertThat(result).isEqualTo(companyResponse);
        assertThat(result.getName()).isEqualTo("삼성전자");
        assertThat(result.getIndustry()).isEqualTo("전자제품 제조");
        assertThat(result.getEmployeeCount()).isEqualTo(267937);
        assertThat(result.getFoundedYear()).isEqualTo(1969);

    }

    @Test
    @DisplayName("신규 회사 등록 테스트")
    void insertCompany() {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .name("LG전자")
                .industry("전자제품 제조")
                .employeeCount(50000)
                .foundedYear(1958)
                .build();
        given(companyRepository.insertCompany(companyRequest)).willReturn(1);

        //when
        companyService.insertCompany(companyRequest);

        //then
        verify(companyRepository, atLeastOnce()).insertCompany(any());
        verify(companyRepository, times(1)).insertCompany(companyRequest);

    }

    @Test
    @DisplayName("회사 정보 수정 테스트")
    void updateCompany() {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .id(1)
                .name("삼성전자(주)")
                .industry("전자제품 제조")
                .employeeCount(270000)
                .foundedYear(1969)
                .build();
        given(companyRepository.updateCompany(companyRequest)).willReturn(1);

        //when
        companyService.updateCompany(companyRequest);

        //then
        verify(companyRepository, atLeastOnce()).updateCompany(any());
        verify(companyRepository).updateCompany(companyRequest);

    }

    @Test
    @DisplayName("회사 삭제 테스트")
    void deleteCompanyById() {

        //given
        given(companyRepository.deleteCompanyById(1)).willReturn(1);

        //when
        companyService.deleteCompanyById(1);

        //then
        verify(companyRepository, atLeastOnce()).deleteCompanyById(1);
    }
}


