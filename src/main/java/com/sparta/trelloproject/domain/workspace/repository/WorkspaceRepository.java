package com.sparta.trelloproject.domain.workspace.repository;

import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
  List<Workspace> findAllByMember(Member member);

  Optional<Workspace> findByMember(Member member);
}
