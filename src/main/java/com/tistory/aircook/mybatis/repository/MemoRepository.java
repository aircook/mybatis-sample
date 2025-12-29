package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemoRepository {

    List<MemoResponse> findMemoAll();
    MemoResponse findMemoById(Integer id);
    Integer insertMemo(MemoRequest memoRequest);
    Integer updateMemo(MemoRequest memoRequest);
    Integer deleteMemoById(Integer id);

}
