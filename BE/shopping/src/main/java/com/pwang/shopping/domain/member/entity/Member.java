package com.pwang.shopping.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {

    @Id
    private String email;

    private String password;
    private String name;

   @Enumerated(EnumType.STRING)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member makeEntity(String name, String email) {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
