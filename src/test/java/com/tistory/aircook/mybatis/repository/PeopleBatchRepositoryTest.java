package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.config.database.MybatisBatchConfig;
import com.tistory.aircook.mybatis.domain.PeopleRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

/**
 * PeopleBatchRepository 통합 테스트
 * @MybatisTest를 사용하여 실제 DB와 연동하여 MyBatis Batch Mapper를 테스트합니다.
 * 
 * 주의: 이 Repository는 @BatchMapper 어노테이션을 사용하여 별도의 SqlSessionFactory를 사용합니다.
 * MybatisBatchConfig를 명시적으로 임포트하여 @BatchMapper 스캔을 활성화합니다.
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MybatisBatchConfig.class)
@Transactional
class PeopleBatchRepositoryTest {

    @Autowired
    private PeopleBatchRepository peopleBatchRepository;

    @Autowired
    @Qualifier("batchSqlSessionTemplate")
    private SqlSessionTemplate batchSqlSessionTemplate;

    @Test
    @DisplayName("배치 방식으로 인물 등록 테스트")
    void insertPeople() {

        //given
        PeopleRequest peopleRequest = new PeopleRequest();
        peopleRequest.setName("배치 테스트 사용자");
        peopleRequest.setBirth("2024-01-01");

        //when
        peopleBatchRepository.insertPeople(peopleRequest);
        
        // 배치 모드에서는 flushStatements()를 호출해야 실제로 실행됩니다.
        List<BatchResult> batchResultList = batchSqlSessionTemplate.flushStatements();
        
        //then
        // flushStatements()가 성공적으로 실행되면 테스트는 통과합니다.
        assertThat(batchResultList.get(0).getUpdateCounts().length).isEqualTo(1);
    }

    @Test
    @DisplayName("배치 방식으로 여러 인물 등록 테스트")
    void insertMultiplePeople() {

        //given
        int insertCount = 5;

        //when
        for (int i = 0; i < insertCount; i++) {
            PeopleRequest peopleRequest = new PeopleRequest();
            peopleRequest.setName("배치 테스트 사용자 " + i);
            peopleRequest.setBirth("2024-01-0" + (i + 1));
            
            peopleBatchRepository.insertPeople(peopleRequest);
        }
        // 배치 모드에서는 flushStatements()를 호출해야 실제로 실행됩니다.
        List<BatchResult> batchResultList = batchSqlSessionTemplate.flushStatements();

        //then
        // flushStatements()가 성공적으로 실행되면 테스트는 통과합니다.
        assertThat(batchResultList.get(0).getUpdateCounts().length).isEqualTo(insertCount);
    }
}


