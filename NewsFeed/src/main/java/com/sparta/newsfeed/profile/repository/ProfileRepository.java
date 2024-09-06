package com.sparta.newsfeed.profile.repository;

import com.sparta.newsfeed.profile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByName(String name);

}
