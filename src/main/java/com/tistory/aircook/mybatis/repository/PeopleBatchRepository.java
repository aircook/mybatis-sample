package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.config.database.mapper.BatchMapper;
import com.tistory.aircook.mybatis.domain.PeopleRequest;

//@Mapper
@BatchMapper
public interface PeopleBatchRepository {

    int insertPeople(PeopleRequest peopleRequest);
}
