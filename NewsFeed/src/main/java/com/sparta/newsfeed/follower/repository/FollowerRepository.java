package com.sparta.newsfeed.follower.repository;

import com.sparta.newsfeed.follower.entity.Follower;
import com.sparta.newsfeed.profile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Follower findByUserAndFollower(User user, User follower);

    Follower findByUserIdAndFollowerId(Long userId, Long followerId);

    List<Follower> findByFollower(User user);

    List<Follower> findByUser(User user);
}
