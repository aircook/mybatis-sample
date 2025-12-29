package com.tistory.aircook.mybatis.controller;

import com.tistory.aircook.mybatis.domain.MemoRequest;
import com.tistory.aircook.mybatis.domain.MemoResponse;
import com.tistory.aircook.mybatis.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @GetMapping
    public ResponseEntity<List<MemoResponse>> findMemoAll() {
        return new ResponseEntity<>(memoService.findMemoAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponse> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(memoService.findMemoById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> insertMemo(@RequestBody MemoRequest memoRequest) {
        return new ResponseEntity<>(memoService.insertMemo(memoRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Integer> updateMemo(@RequestBody MemoRequest memoRequest) {
        return new ResponseEntity<>(memoService.updateMemo(memoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteMemoById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(memoService.deleteMemoById(id), HttpStatus.OK);
    }

}
