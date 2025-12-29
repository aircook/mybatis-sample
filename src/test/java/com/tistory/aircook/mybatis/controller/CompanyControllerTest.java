package com.tistory.aircook.mybatis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.aircook.mybatis.domain.CompanyRequest;
import com.tistory.aircook.mybatis.domain.CompanyResponse;
import com.tistory.aircook.mybatis.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CompanyController 단위 테스트
 * MockMvc를 사용하여 REST API 엔드포인트를 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(companyController)
                .build();
    }

    @Test
    @DisplayName("전체 회사 목록 조회 테스트")
    void findCompanyAll() throws Exception {

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
        companyResponseList.add(CompanyResponse.builder()
                .id(3)
                .name("카카오")
                .industry("인터넷 서비스")
                .employeeCount(3000)
                .foundedYear(2010)
                .build());
        //given stub
        given(companyService.findCompanyAll()).willReturn(companyResponseList);

        //when
        mockMvc.perform(get("/companies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lessThanOrEqualTo(3)));

        //then
        verify(companyService).findCompanyAll();

    }

    @Test
    @DisplayName("아이디로 회사 조회 테스트")
    void findCompanyById() throws Exception {

        //given
        CompanyResponse companyResponse = CompanyResponse.builder()
                .id(1)
                .name("삼성전자")
                .industry("전자제품 제조")
                .employeeCount(267937)
                .foundedYear(1969)
                .build();
        //given stub
        given(companyService.findCompanyById(1)).willReturn(companyResponse);

        //when
        mockMvc.perform(get("/companies/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("삼성전자"))
                .andExpect(jsonPath("$.industry").value("전자제품 제조"))
                .andExpect(jsonPath("$.employeeCount").value(267937))
                .andExpect(jsonPath("$.foundedYear").value(1969));

        //then
        verify(companyService).findCompanyById(1);

    }

    @Test
    @DisplayName("신규 회사 등록 테스트")
    void insertCompany() throws Exception {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .name("LG전자")
                .industry("전자제품 제조")
                .employeeCount(50000)
                .foundedYear(1958)
                .build();
        //given stub
        given(companyService.insertCompany(any(CompanyRequest.class))).willReturn(anyInt());

        //when
        mockMvc.perform(post("/companies")
                        .content(objectMapper.writeValueAsString(companyRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        verify(companyService).insertCompany(any(CompanyRequest.class));
    }

    @Test
    @DisplayName("회사 정보 수정 테스트")
    void updateCompany() throws Exception {

        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .id(1)
                .name("삼성전자(주)")
                .industry("전자제품 제조")
                .employeeCount(270000)
                .foundedYear(1969)
                .build();
        //given stub
        given(companyService.updateCompany(any(CompanyRequest.class))).willReturn(1);

        //when
        mockMvc.perform(put("/companies")
                        .content(objectMapper.writeValueAsString(companyRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(companyService).updateCompany(any(CompanyRequest.class));

    }

    @Test
    @DisplayName("회사 삭제 테스트")
    void deleteCompanyById() throws Exception {

        //given stub
        given(companyService.deleteCompanyById(1)).willReturn(1);

        //when
        mockMvc.perform(delete("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());

        //then
        verify(companyService).deleteCompanyById(1);
    }
}

