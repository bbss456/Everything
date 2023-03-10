package com.pwang.shopping.domain.member.controller;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.requestDTO.MemberCreateRequestDTO;
import com.pwang.shopping.domain.member.service.Memberservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;


@RestController
@RequestMapping("/shopping")
public class MemberController {

    @Autowired
    Memberservice memberservice;

    public HttpHeaders header() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return  header;
    }

    @PostMapping("/api/v1/member")
    public ResponseEntity<Boolean> create(@RequestBody MemberCreateRequestDTO memberCreateRequestDTO) {

        memberservice.create(memberCreateRequestDTO);
        return new ResponseEntity<Boolean>(Boolean.TRUE,this.header(), HttpStatus.OK);
    }

}
