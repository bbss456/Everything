package com.pwang.shopping.domain.member.requestdto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;


@Data
@Builder

public class MemberCreateRequestDTO {
    @Id
    private String email;
    private String password;
    private String name;
}


