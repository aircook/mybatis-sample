package com.tistory.aircook.mybatis.repository;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.domain.PeopleRequest;
import com.tistory.aircook.mybatis.domain.PeopleResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

@Mapper
public interface MemoRepository {

    List<MemoResponse> findMemoAll();
    MemoResponse findMemoById(Integer id);
    Integer insertMemo(MemoRequest memoRequest);
    Integer updateMemo(MemoRequest memoRequest);
    Integer deleteMemoById(Integer id);

}
