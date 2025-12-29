package com.tistory.aircook.mybatis.service;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "memoCache")
public class MemoService {

    private final MemoRepository memoRepository;

    //@Cacheable --> 캐시 저장 or 조회
    //key 를 all 로 설정했기 때문에 memoCache::all 이라는 key 값에 List<MemoResponse> 데이터가 저장됩니다.
    //@Cacheable(key = "'all'")
    //@Cacheable(key="#root.target.CONSTANT")
    //SpEL에 enum 사용
    @Cacheable(key = "T(com.tistory.aircook.mybatis.config.cache.CacheKeyEnum).MEMO_ALL")
    public List<MemoResponse> findMemoAll() {
        return memoRepository.findMemoAll();
    }

    //unless = "#result == null" 조건을 추가하여 DB 에 없는 데이터인 경우 캐싱하지 않도록 했습니다.
    //만약 이 조건을 추가하지 않으면 null 값도 캐싱 대상이 됩니다.
    @Cacheable(key = "#id", unless = "#result == null")
    public MemoResponse findMemoById(Integer id) {
        return memoRepository.findMemoById(id);
    }

    //@CacheEvict --> 캐시제거 
    @CacheEvict(key = "T(com.tistory.aircook.mybatis.config.cache.CacheKeyEnum).MEMO_ALL")
    public Integer insertMemo(MemoRequest memoRequest) {
        return memoRepository.insertMemo(memoRequest);
    }

    //@CachePut(key = "#memoRequest.id") --> 캐시 생성
    // --> 캐싱아이디는 알수 있지만 return 결과를 캐싱하는데 결과가 건수임으로 캐싱을 할수가 없다.
    // --> JPA를 사용하던가.. 조회를 한번더 하던가..아니면 다음처럼 캐시 삭제하던가
    //@CacheEvict(key = "'all'")
    @Caching(evict = {
            @CacheEvict(key = "T(com.tistory.aircook.mybatis.config.cache.CacheKeyEnum).MEMO_ALL"),
            @CacheEvict(key = "#memoRequest.id")
    })
    public Integer updateMemo(MemoRequest memoRequest) {
        return memoRepository.updateMemo(memoRequest);
    }

    @Caching(evict = {
            @CacheEvict(key = "T(com.tistory.aircook.mybatis.config.cache.CacheKeyEnum).MEMO_ALL"),
            @CacheEvict(key = "#id")
    })
    public Integer deleteMemoById(Integer id) {
        return memoRepository.deleteMemoById(id);
    }

}
