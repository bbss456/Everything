package com.pwang.shopping.domain.address.entity;

import com.pwang.shopping.domain.member.entity.Member;
import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String si;
    private String gu;
    private String dongBub;
    private String jibun;
    private String doroName;
    private String detail;
}
