package com.sparta.trelloproject.domain.member.repository;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.member.enums.Assign;
import com.sparta.trelloproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByUserId(Long userId);

  boolean existsByAssign(Assign assign);

  List<Member> findAllByUserId(Long userId);

  Optional<Member> findByUserId(Long userId);

  Optional<Member> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);

  Optional<Member> findByUser(User user);
}
