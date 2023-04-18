package com.pwang.shopping.domain.member.responsedto;

import com.pwang.shopping.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMemberDTO {

    private String email;
    private String name;
    private String password;
    private String gender;
    private String birthyear;
    private String mobile;

    public ResponseMemberDTO(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.password = member.getPassword();
        this.gender = member.getGender();
        this.birthyear = member.getBirthyear();
        this.mobile = member.getMobile();
    }
}
