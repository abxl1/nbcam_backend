package com.sparta.newsfeed.follower.service;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.follower.dto.requestDto.FollowerRequestDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowAcceptResponseDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowerGetResponseDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowerSaveResponseDto;
import com.sparta.newsfeed.follower.entity.Follower;
import com.sparta.newsfeed.follower.repository.FollowerRepository;
import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final ProfileRepository userRepository;

    // post - 친구 팔로우
    @Transactional
    public FollowerSaveResponseDto saveFollower(AuthUser authUser, FollowerRequestDto followerRequestDto) {
        // 현재 사용자의 id가 존재하는지 체크, 없는 사용자일 시에는 에러 던지기
        User user = userRepository.findById(authUser.getId()).orElseThrow(NullPointerException::new);

        // 친구 신청을 보낼 유저가 존재하는지 체크, 없는 사용자일 때 예외 처리
        User follower = userRepository.findByEmail(followerRequestDto.getEmail());
        if (follower == null) {
            throw new NullPointerException("사용자가 존재하지 않습니다.");
        }

        // 본인에게 친구 신청을 하는지 체크
        if (follower.getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인에게 친구 신청을 보낼 수 없습니다.");
        }

        // 이미 신청한 유저에게 신청을 보내는지 체크
        if (followerRepository.findByUserAndFollower(user, follower) != null || followerRepository.findByUserAndFollower(follower, user) != null) {
            throw new IllegalArgumentException("이미 친구 신청을 보냈습니다.");
        }

        Follower newFollower = new Follower(user, follower);
        Follower saveFollower = followerRepository.save(newFollower);
        return new FollowerSaveResponseDto(saveFollower);
    }

    // post - 친구 신청 수락 요청 보내기
    @Transactional
    public FollowAcceptResponseDto acceptFollow(AuthUser authUser, FollowerRequestDto followerRequestDto) {
        // 현재 사용자의 id, 수락자의 email이 존재하는지 체크, 없는 사용자일 시에는 에러 던지기
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(NullPointerException::new);
        User follower = userRepository.findByEmail(followerRequestDto.getEmail());

        Follower newFollowRequest = followerRepository.findByUserIdAndFollowerId(follower.getId(), user.getId());
        if (newFollowRequest == null) {
            throw new NullPointerException("no Follower object");
        }
        newFollowRequest.changeAcceptStatus();
        Follower followAccept = followerRepository.save(newFollowRequest);
        return new FollowAcceptResponseDto(follower, followAccept.getStatus());
    }

    @Transactional
    public void deniedFollow(AuthUser authUser, FollowerRequestDto followerRequestDto) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(NullPointerException::new);
        User follower = userRepository.findByEmail(followerRequestDto.getEmail());

        Follower newFollowRequest = followerRepository.findByUserIdAndFollowerId(follower.getId(), user.getId());
        if (newFollowRequest == null) {
            throw new NullPointerException("no Follower object");
        }
        followerRepository.delete(newFollowRequest);
    }

    public List<FollowerGetResponseDto> getFollowList(AuthUser authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(NullPointerException::new);
        List<Follower> followerList = followerRepository.findByFollower(user);
        List<FollowerGetResponseDto> dtoList = new ArrayList<>();
        for (Follower follower : followerList) {
            if (follower.getStatus().equals(Follower.Status.pending)) {
                FollowerGetResponseDto dto = new FollowerGetResponseDto(follower.getUser().getEmail());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    // delete - 친구 팔로우 취소
    @Transactional
    public void deleteFollower(AuthUser authUser, FollowerRequestDto followerRequestDto) {
        // 없는 사용자를 팔로우 취소하려고 하는지 체크
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new NullPointerException("해당 사용자가 존재하지 않습니다."));
        User follower = userRepository.findByEmail(followerRequestDto.getEmail());
        if (follower == null) {
            throw new NullPointerException("사용자가 존재하지 않습니다.");
        }

        Follower followerRelationship = followerRepository.findByUserIdAndFollowerId(user.getId(), follower.getId());
        Follower followerRelationship2 = followerRepository.findByUserIdAndFollowerId(follower.getId(), user.getId());
        if (followerRelationship != null && followerRelationship.getStatus() == Follower.Status.accepted) {
            followerRepository.delete(followerRelationship);
        } else if (followerRelationship2 != null && followerRelationship2.getStatus() == Follower.Status.accepted) {
            followerRepository.delete(followerRelationship2);
        } else {
            throw new IllegalArgumentException("두 유저는 친구 관계가 아닙니다.");
        }
    }
}
