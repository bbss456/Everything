package com.pwang.shopping.domain.member.entity;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    SessionUser() {}

    public SessionUser(Member user) {
        this.name = user.getName();
        this.email = user.getEmail();

    }
    private String name;

    private String nickname;

    private String email;

    private String picture;

    private String gender;

    private String age;
}