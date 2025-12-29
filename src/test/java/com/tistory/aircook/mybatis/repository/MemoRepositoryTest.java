package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    @DisplayName("전체 목록 조회 테스트")
    void findMemoAll() {

        //given
        MemoRequest memoRequest = MemoRequest.builder().memo("단위테스트1입니다.").writer("아이작 뉴턴").build();

        //when
        memoRepository.insertMemo(memoRequest);
        List<MemoResponse> memoList = memoRepository.findMemoAll();

        //then
        //assertj, assertThat(actual).isEqualTo(expected);
        //3 data already exist
        assertThat(memoList.size()).isGreaterThan(3);

    }

    @Test
    @DisplayName("아이디 검색 조회 테스트")
    void findMemoById() {

        //given
        MemoRequest memoRequest = MemoRequest.builder().memo("단위테스트1입니다.").writer("아이작 뉴턴").build();

        //when
        memoRepository.insertMemo(memoRequest);
        MemoResponse memoResponse1 = memoRepository.findMemoById(memoRequest.getId());
        MemoResponse memoResponse2 = memoRepository.findMemoById(300);

        //then
        assertThat(memoResponse1.getId()).isEqualTo(memoRequest.getId());
        assertThat(memoResponse2).isNull();

    }

    @Test
    @DisplayName("신규저장 테스트")
    void insertMemo() {

        //given
        MemoRequest memoRequest = MemoRequest.builder().memo("단위테스트1입니다.").writer("아이작 뉴턴").build();

        //when
        memoRepository.insertMemo(memoRequest);
        MemoResponse memoResponse = memoRepository.findMemoById(memoRequest.getId());

        //then
        //junit, assertEquals(expected, actual);
        assertEquals(memoRequest.getId(), memoResponse.getId());
        //assertj, assertThat(actual).isEqualTo(expected);
        assertThat(memoResponse.getId()).isEqualTo(memoRequest.getId());
        assertThat(memoResponse.getMemo()).isEqualTo(memoRequest.getMemo());
        assertThat(memoResponse.getWriter()).isEqualTo(memoRequest.getWriter());

    }

    @Test
    @DisplayName("수정 테스트")
    void updateMemo() {

        //given
        MemoRequest memoRequest = MemoRequest.builder().id(1).memo("단위테스트 수정 테스트입니다.").writer("아이작 뉴턴").build();

        //when
        memoRepository.updateMemo(memoRequest);
        MemoResponse memoResponse = memoRepository.findMemoById(memoRequest.getId());

        //then
        //assertj, assertThat(actual).isEqualTo(expected);
        assertThat(memoResponse.getMemo()).isEqualTo(memoRequest.getMemo());

    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteMemoById() {

        //given
        //1 is the data that exists through spring boot initialization
        Integer id = 1;

        //when
        memoRepository.deleteMemoById(id);
        MemoResponse memoResponse = memoRepository.findMemoById(id);
        
        //then
        assertThat(memoResponse).isNull();

    }
}