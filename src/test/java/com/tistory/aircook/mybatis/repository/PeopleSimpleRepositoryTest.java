package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.domain.PeopleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// JUnit5 사용 시 작성, MybatisTest 2.0.1버전 이상에서 생략 가능
// @ExtendWith(SpringExtension.class)
// JUnit4 사용 시 작성
// @RunWith(SpringRunner.class)

// @MybatisTest 어노테이션 추가
@MybatisTest
// 실 데이터베이스에 연결 시 필요한 어노테이션
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PeopleSimpleRepositoryTest {

    @Autowired
    private PeopleSimpleRepository peopleSimpleRepository;

    @Test
    @DisplayName("전체 목록 조회 테스트")
    void selectPeopleNormal() {
        // when
        List<PeopleResponse> peopleResponses = peopleSimpleRepository.selectPeopleNormal();

        // then
        assertThat(peopleResponses.size()).isEqualTo(10);

    }

    @Test
    @DisplayName("특정 인물 조회 테스트")
    void findById() {
        // given
        String id = "1";

        // when
        PeopleResponse peopleResponse = peopleSimpleRepository.findById(id);

        // then
        assertThat(peopleResponse.getId()).isEqualTo(Integer.parseInt("1"));

    }
}