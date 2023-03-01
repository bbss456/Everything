package com.pwang.shopping.domain.member.repository;

import com.pwang.shopping.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository  extends JpaRepository<Member,String> {
}
