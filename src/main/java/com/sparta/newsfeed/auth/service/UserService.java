package com.sparta.newsfeed.auth.service;

import com.sparta.newsfeed.auth.config.PasswordEncoder;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.auth.dto.UserRequestDto;
import com.sparta.newsfeed.auth.jwt.JwtUtil;
import com.sparta.newsfeed.profile.dto.RequestUserDto;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.profile.repository.ProfileRepository;
import com.sparta.newsfeed.profile.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseUserDto signup(RequestUserDto requestDto) {
        String userName = requestDto.getName();
        String email = requestDto.getEmail();

        // 회원 중복 확인
        if (userRepository.findByName(userName) != null) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 이메일 중복 확인
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        if(!ProfileService.isValidPassword(requestDto.getPassword())){
            throw new IllegalArgumentException("하나 이상의 대소문자, 특수문자, 숫자를 포함해 8글자 이상이어야 합니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 등록
        User newUser = new User(userName, encodedPassword, email);
        if(requestDto.getPhoneNumber() != null)
            newUser.setPhoneNumber(requestDto.getPhoneNumber());
        if(requestDto.getNickname() != null)
            newUser.setNickname(requestDto.getNickname());
        if(requestDto.getBio() != null)
            newUser.setBio(requestDto.getBio());
        return new ResponseUserDto(userRepository.save(newUser));
    }

    @Transactional
    public String login(UserRequestDto requestDto) {
        // 사용자 확인
        User user = userRepository.findByEmail(requestDto.getEmail());

        // 사용자 존재 여부 확인
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 후 반환
        return jwtUtil.createToken(user.getId());
    }

    @Transactional
    public void withdrawal(AuthUser authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
    }
}