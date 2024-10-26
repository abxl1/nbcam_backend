package com.sparta.trelloproject.domain.user.entity;

import com.sparta.trelloproject.common.entity.Timestamped;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.card.entity.CardAssignee;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardAssignee> cardAssignees = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    private User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(
                authUser.getUserId(),
                authUser.getEmail(),
                UserRole.of(authUser.getAuthorities().stream().findFirst().get().getAuthority())
        );
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void toggleDelete() {
        this.isDeleted = true;
    }
}