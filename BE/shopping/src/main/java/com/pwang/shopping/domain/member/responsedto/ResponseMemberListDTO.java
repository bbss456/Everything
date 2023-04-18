package com.pwang.shopping.domain.member.responsedto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseMemberListDTO {

    private int count;
    private List<ResponseMemberDTO> MemberList = new ArrayList<>();
}
