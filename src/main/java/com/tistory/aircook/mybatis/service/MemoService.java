package com.tistory.aircook.mybatis.service;


import com.google.common.collect.Lists;
import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.domain.PeopleRequest;
import com.tistory.aircook.mybatis.domain.PeopleResponse;
import com.tistory.aircook.mybatis.repository.MemoRepository;
import com.tistory.aircook.mybatis.repository.PeopleBatchRepository;
import com.tistory.aircook.mybatis.repository.PeopleSimpleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemoService {

    private final MemoRepository memoRepository;

    public List<MemoResponse> findMemoAll() {
        return memoRepository.findMemoAll();
    }
    public MemoResponse findMemoById(Integer id) {
        return memoRepository.findMemoById(id);
    }
    public Integer insertMemo(MemoRequest memoRequest) {
        return memoRepository.insertMemo(memoRequest);
    }
    public Integer updateMemo(MemoRequest memoRequest) {
        return memoRepository.updateMemo(memoRequest);
    }
    public Integer deleteMemoById(Integer id) {
        return memoRepository.deleteMemoById(id);
    }

}
