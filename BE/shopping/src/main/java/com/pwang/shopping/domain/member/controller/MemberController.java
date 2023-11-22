package com.pwang.shopping.domain.member.controller;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.requestdto.MemberCreateRequestDTO;
import com.pwang.shopping.domain.member.responsedto.ResponseMemberDTO;
import com.pwang.shopping.domain.member.responsedto.ResponseMemberListDTO;
import com.pwang.shopping.domain.member.service.Memberservice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class MemberController {

    private final Memberservice memberservice;

    public HttpHeaders header() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return  header;
    }

    @PostMapping("/api/v1/member")
    public ResponseEntity<Boolean> createMember(@RequestBody MemberCreateRequestDTO memberCreateRequestDTO) {

        memberservice.create(memberCreateRequestDTO);
        return new ResponseEntity<Boolean>(Boolean.TRUE,this.header(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/member/{email}/{type}")
    public ResponseEntity<ResponseMemberDTO> getMember(@PathVariable("email") String email, @PathVariable("type") String type) {

        Member member = memberservice.findMember(email, type);
        ResponseMemberDTO MemberCreateRequestDTO = ResponseMemberDTO
                .builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .birthyear(member.getBirthyear())
                .mobile(member.getMobile())
                .gender(member.getGender())
                .name(member.getName())
                .build();
        return new ResponseEntity<ResponseMemberDTO>(MemberCreateRequestDTO, this.header(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/members")
    public ResponseEntity<ResponseMemberListDTO> getMemberList(final Pageable pageable) {

        return new ResponseEntity<ResponseMemberListDTO>(memberservice.findAllMember(pageable), this.header(), HttpStatus.OK);
    }

}
