package com.tistory.aircook.mybatis.controller;

import com.tistory.aircook.mybatis.domain.PeopleResponse;
import com.tistory.aircook.mybatis.service.PeopleService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PeopleController 단위 테스트
 * MockMvc를 사용하여 REST API 엔드포인트를 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class PeopleControllerTest {

    @InjectMocks
    private PeopleController peopleController;

    @Mock
    private PeopleService peopleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(peopleController)
                .build();
    }

    @Test
    @DisplayName("일반 방식으로 전체 인물 목록 조회 테스트")
    void selectPeopleNormal() throws Exception {

        //given
        List<PeopleResponse> peopleResponseList = new ArrayList<>();
        peopleResponseList.add(PeopleResponse.builder()
                .id(1)
                .name("알베르트 아인슈타인")
                .birth("1879-03-14")
                .build());
        peopleResponseList.add(PeopleResponse.builder()
                .id(2)
                .name("조지 워싱턴")
                .birth("1732-02-22")
                .build());
        given(peopleService.selectPeopleNormal()).willReturn(peopleResponseList);

        //when
        mockMvc.perform(get("/people/normal")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("알베르트 아인슈타인"))
                .andExpect(jsonPath("$[1].name").value("조지 워싱턴"));

        //then
        verify(peopleService).selectPeopleNormal();
    }

    @Test
    @DisplayName("ResultHandler 방식으로 전체 인물 목록 조회 테스트")
    void selectPeopleHandler() throws Exception {

        //given
        List<PeopleResponse> peopleResponseList = new ArrayList<>();
        peopleResponseList.add(PeopleResponse.builder()
                .id(1)
                .name("알베르트 아인슈타인 [handler]")
                .birth("1879-03-14")
                .build());
        given(peopleService.selectPeopleHandler()).willReturn(peopleResponseList);

        //when
        mockMvc.perform(get("/people/handler")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("알베르트 아인슈타인 [handler]"));

        //then
        verify(peopleService).selectPeopleHandler();
    }

    @Test
    @DisplayName("Cursor 방식으로 전체 인물 목록 조회 테스트")
    void selectPeopleCursor() throws Exception {

        //given
        List<PeopleResponse> peopleResponseList = new ArrayList<>();
        peopleResponseList.add(PeopleResponse.builder()
                .id(1)
                .name("알베르트 아인슈타인 [cursor]")
                .birth("1879-03-14")
                .build());
        given(peopleService.selectPeopleCursor()).willReturn(peopleResponseList);

        //when
        mockMvc.perform(get("/people/cursor")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("알베르트 아인슈타인 [cursor]"));

        //then
        verify(peopleService).selectPeopleCursor();
    }

    @Test
    @DisplayName("단순 방식으로 대량 인물 입력 테스트")
    void insertSimplePeoples() throws Exception {

        //given
        doNothing().when(peopleService).insertSimplePeoples();

        //when
        mockMvc.perform(get("/people/simple")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(peopleService).insertSimplePeoples();
    }

    @Test
    @DisplayName("배치 방식으로 대량 인물 입력 테스트")
    void insertBatchPeoples() throws Exception {

        //given
        doNothing().when(peopleService).insertBatchPeoples();

        //when
        mockMvc.perform(get("/people/batch")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(peopleService).insertBatchPeoples();
    }

    @Test
    @DisplayName("단위별 배치 방식으로 대량 인물 입력 테스트")
    void insertBatchPeoplesByUnit() throws Exception {

        //given
        doNothing().when(peopleService).insertBatchPeoplesByUnit();

        //when
        mockMvc.perform(get("/people/batch-by-unit")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(peopleService).insertBatchPeoplesByUnit();
    }

    @Test
    @DisplayName("아이디로 인물 조회 테스트")
    void findById() throws Exception {

        //given
        PeopleResponse peopleResponse = PeopleResponse.builder()
                .id(1)
                .name("알베르트 아인슈타인")
                .birth("1879-03-14")
                .build();
        given(peopleService.findById("1")).willReturn(peopleResponse);

        //when
        mockMvc.perform(get("/people/peoples/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("알베르트 아인슈타인"))
                .andExpect(jsonPath("$.birth").value("1879-03-14"));

        //then
        verify(peopleService).findById("1");
    }
}

