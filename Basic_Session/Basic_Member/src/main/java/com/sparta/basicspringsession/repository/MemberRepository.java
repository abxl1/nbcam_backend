package com.sparta.basicspringsession.repository;

import com.sparta.basicspringsession.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}