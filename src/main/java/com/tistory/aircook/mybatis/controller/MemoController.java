package com.tistory.aircook.mybatis.controller;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.domain.PeopleResponse;
import com.tistory.aircook.mybatis.service.MemoService;
import com.tistory.aircook.mybatis.service.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @GetMapping
    public List<MemoResponse> findMemoAll() {
        return memoService.findMemoAll();
    }

    @GetMapping("/{id}")
    public MemoResponse findById(@PathVariable("id") Integer id) {
        return memoService.findMemoById(id);
    }

    @PostMapping
    public Integer insertMemo(@RequestBody MemoRequest memoRequest) {
        return memoService.insertMemo(memoRequest);
    }

    @PutMapping
    public Integer updateMemo(@RequestBody MemoRequest memoRequest) {
        return memoService.updateMemo(memoRequest);
    }

    @DeleteMapping("/{id}")
    public Integer deleteMemoById(@PathVariable("id") Integer id) {
        return memoService.deleteMemoById(id);
    }

}
