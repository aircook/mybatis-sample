package com.tistory.aircook.mybatis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.service.MemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * https://velog.io/@chrkb1569/JUnit-Controller-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1
 */
@ExtendWith(MockitoExtension.class)
class MemoControllerTest {

    //@InjectMocks --> @Mock 또는 @Spy로 생성된 가짜 객체를 자동으로 주입시켜주는 객체이다.
    @InjectMocks
    private MemoController memoController;

    //@Mock --> Mock 객체를 생성한다. 실제로 메서드는 갖고 있지만 내부 구현이 없는 상태이다.
    @Mock
    private MemoService memoService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(memoController)
                .build();
    }

    @Test
    @DisplayName("전체 목록 조회 테스트")
    void findMemoAll() throws Exception {

        //given
        List<MemoResponse> memoResponseList = new ArrayList<>();
        memoResponseList.add(MemoResponse.builder().id(1).memo("메모내용1입니다.").writer("레옹 푸코").writeAt("2023-12-18 01:37:50").build());
        memoResponseList.add(MemoResponse.builder().id(2).memo("메모내용2입니다.").writer("레옹 푸코").writeAt("2023-12-18 13:55:59").build());
        memoResponseList.add(MemoResponse.builder().id(3).memo("메모내용3입니다.").writer("레옹 푸코").writeAt("2023-12-19 13:00:10").build());
        //given stub
        given(memoService.findMemoAll()).willReturn(memoResponseList);

        //when
        mockMvc.perform(get("/memos")
            .accept(MediaType.APPLICATION_JSON)
            //.header(HttpHeaders.AUTHORIZATION, "ADMIN_ACCESS_TOKEN")
            //.param("searchType", "searchString")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(lessThanOrEqualTo(3)));

        //then
        //memoService.findMemoAll()가 실행되었는지 검증
        verify(memoService).findMemoAll();

    }

    @Test
    @DisplayName("아이디 검색 조회 테스트")
    void findById() throws Exception {

        //given
        MemoResponse memoResponse = MemoResponse.builder().id(1).memo("메모내용1입니다.").writer("레옹 푸코").writeAt("2023-12-18 01:37:50").build();
        //given stub
        given(memoService.findMemoById(1)).willReturn(memoResponse);

        //when
        mockMvc.perform(get("/memos/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.memo").value("메모내용1입니다."));

        //then
        //memoService.findMemoById(1)이 실행되었는지 검증
        verify(memoService).findMemoById(1);

    }

    @Test
    @DisplayName("신규저장 테스트")
    void insertMemo() throws Exception {

        //given
        MemoRequest memoRequest = MemoRequest.builder().memo("메모내용1입니다.").writer("레옹 푸코").build();
        //given stub
        given(memoService.insertMemo(memoRequest)).willReturn(anyInt());

        //when
        mockMvc.perform(post("/memos")
            .content(objectMapper.writeValueAsString(memoRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        //then
        //memoService.insertMemo(memoRequest)이 실행되었는지 검증
        verify(memoService).insertMemo(memoRequest);
    }

    @Test
    @DisplayName("수정 테스트")
    void updateMemo() throws Exception {

        //given
        MemoRequest memoRequest = MemoRequest.builder().id(1).memo("메모내용1입니다. 수정").writer("레옹 푸코 수정").build();
        //given stub
        given(memoService.updateMemo(memoRequest)).willReturn(1);

        //when
        mockMvc.perform(put("/memos")
            .content(objectMapper.writeValueAsString(memoRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //then
        //memoService.updateMemo(memoRequest)이 실행되었는지 검증
        verify(memoService).updateMemo(memoRequest);

    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteMemoById() throws Exception {

        //given stub
        given(memoService.deleteMemoById(1)).willReturn(anyInt());

        //when
        mockMvc.perform(delete("/memos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNumber());

        //then
        verify(memoService).deleteMemoById(1);
    }
}