package com.tistory.aircook.mybatis.controller;

import com.tistory.aircook.mybatis.domain.CompanyRequest;
import com.tistory.aircook.mybatis.domain.CompanyResponse;
import com.tistory.aircook.mybatis.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> findCompanyAll() {
        return new ResponseEntity<>(companyService.findCompanyAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> findCompanyById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(companyService.findCompanyById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> insertCompany(@RequestBody CompanyRequest companyRequest) {
        return new ResponseEntity<>(companyService.insertCompany(companyRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Integer> updateCompany(@RequestBody CompanyRequest companyRequest) {
        return new ResponseEntity<>(companyService.updateCompany(companyRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCompanyById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(companyService.deleteCompanyById(id), HttpStatus.OK);
    }

}



