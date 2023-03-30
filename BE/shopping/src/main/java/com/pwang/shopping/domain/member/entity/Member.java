package com.pwang.shopping.domain.member.entity;

import com.nimbusds.jose.shaded.json.JSONObject;
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
    private String gender;
    private String birthyear;
    private String mobile;

   @Enumerated(EnumType.STRING)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member makeEntityOfNamver(JSONObject jsonObject) {
        String email = (String) jsonObject.get("email");
        String name = (String) jsonObject.get("name");
        String gender = (String) jsonObject.get("gender");
        String birthyear = (String) jsonObject.get("birthyear");
        String mobile = (String) jsonObject.get("mobile");

        return Member.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .birthyear(birthyear)
                .mobile(mobile)
                .role(Role.USER)
                .build();
    }
}

