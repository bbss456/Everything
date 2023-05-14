package com.pwang.shopping.domain.member.repository;

import com.pwang.shopping.domain.member.entity.Member;
import com.pwang.shopping.domain.member.entity.OAuthType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository  extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndType(String email, OAuthType type);
    Page<Member> findAll(Pageable pageable);
}
