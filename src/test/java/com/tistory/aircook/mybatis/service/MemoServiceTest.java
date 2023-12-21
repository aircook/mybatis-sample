package com.tistory.aircook.mybatis.service;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.repository.MemoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * https://m.blog.naver.com/sosow0212/222838356590
 * */
@ExtendWith(MockitoExtension.class)
class MemoServiceTest {

    //@InjectMocks --> @Mock 또는 @Spy로 생성된 가짜 객체를 자동으로 주입시켜주는 객체이다.
    @InjectMocks
    MemoService memoService;

    //@Mock --> Mock 객체를 생성한다. 실제로 메서드는 갖고 있지만 내부 구현이 없는 상태이다.
    @Mock
    MemoRepository memoRepository;

    @Test
    @DisplayName("전체 목록 조회 테스트")
    void findMemoAll() {

        //BDD는 Behavior-Driven Development의 약자로 행위 주도 개발을 말한다.
        //테스트 대상의 상태의 변화를 테스트하는 것이고, 시나리오를 기반으로 테스트하는 패턴을 권장한다.
        //여기서 권장하는 기본 패턴은 Given, When, Then 구조

        //given
        List<MemoResponse> memoResponseList = new ArrayList<>();
        MemoResponse memoResponse1 = MemoResponse.builder().id(1).memo("메모내용1입니다.").writer("레옹 푸코").writeAt("2023-12-18 01:37:50").build();
        MemoResponse memoResponse2 = MemoResponse.builder().id(1).memo("메모내용2입니다.").writer("레옹 푸코").writeAt("2023-12-18 13:55:59").build();
        memoResponseList.add(memoResponse1);
        memoResponseList.add(memoResponse2);
        given(memoRepository.findMemoAll()).willReturn(memoResponseList);

        //when
        List<MemoResponse> result = memoService.findMemoAll();

        //then
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("아이디 검색 조회 테스트")
    void findMemoById() {
        //given
        MemoResponse memoResponse = MemoResponse.builder().id(1).memo("메모내용1입니다.").writer("레옹 푸코").writeAt("2023-12-18 01:37:50").build();
        given(memoRepository.findMemoById(1)).willReturn(memoResponse);

        //when
        MemoResponse result = memoService.findMemoById(1);

        //then
        assertThat(result).isEqualTo(memoResponse);

    }

    @Test
    @DisplayName("신규저장 테스트")
    void insertMemo() {

        //given
        MemoRequest memoRequest = MemoRequest.builder().memo("단위테스트1입니다.").writer("아이작 뉴턴").build();
        given(memoRepository.insertMemo(memoRequest)).willReturn(1);

        //when, memoService.insertMemo()를 호출하면 memoRepository.insertMemo() 가 호출됨
        memoService.insertMemo(memoRequest);

        //then
        //memoRepository.insertMemo()가 한번이상 실행되었는지 검증
        verify(memoRepository, atLeastOnce()).insertMemo(any());
        //memoRepository.insertMemo(memoRequest)가 한번이상 실행되었는지 검증
        verify(memoRepository, times(1)).insertMemo(memoRequest);

    }

    @Test
    @DisplayName("수정 테스트")
    void updateMemo() {

        //given
        MemoRequest memoRequest = MemoRequest.builder().id(1).memo("단위테스트1입니다.").writer("아이작 뉴턴").build();
        given(memoRepository.updateMemo(memoRequest)).willReturn(1);

        //when, memoService.updateMemo()를 호출하면 memoRepository.updateMemo() 가 호출됨
        memoService.updateMemo(memoRequest);

        //then
        //memoRepository.updateMemo()가 한번이상 실행되었는지 검증
        verify(memoRepository, atLeastOnce()).updateMemo(any());
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteMemoById() {

        //given
        given(memoRepository.deleteMemoById(1)).willReturn(1);

        //when, memoService.deleteMemoById()를 호출하면 memoRepository.deleteMemoById() 가 호출됨
        memoService.deleteMemoById(1);

        //then
        //memoRepository.deleteMemoById()가 한번이상 실행되었는지 검증
        verify(memoRepository, atLeastOnce()).deleteMemoById(1);
    }
}