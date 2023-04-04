package com.pwang.shopping.domain.member.repository;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.OAuthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member,String> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndType(String email, OAuthType type);
}
