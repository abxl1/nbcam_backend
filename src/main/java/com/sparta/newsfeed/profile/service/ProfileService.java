package com.sparta.newsfeed.profile.service;

import com.sparta.newsfeed.auth.config.PasswordEncoder;
import com.sparta.newsfeed.profile.dto.RequestUserDto;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    // 대소문자 포함 + 특수문자, 숫자 하나 이상 포함된 영문 8글자 이상 비밀번호
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public ResponseUserDto getprofile(Long userId, Long targetId) {
        User search_user = profileRepository.findById(targetId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        ResponseUserDto res = new ResponseUserDto();
        
        //본인이 아닐 시 민감 정보 출력안함
        if(Objects.equals(userId, targetId)){
            res.setName(search_user.getName());
            res.setPhoneNumber(search_user.getPhoneNumber());
        }

        res.setEmail(search_user.getEmail());
        res.setNickname(search_user.getNickname());
        res.setBio(search_user.getBio());
        res.setBirthday(search_user.getBirthday());

        return res;
    }

    //프로필 수정
    @Transactional
    public ResponseUserDto updateprofile(Long userId, RequestUserDto requestDto) {
        User user = profileRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("올바른 요청이 아닙니다."));
        String editPassword = requestDto.getEditPassword();
        String input_password = requestDto.getInputPassword();

        //패스워드 수정 시 본인확인을 위해 비밀번호를 확인
        if(requestDto.getId() != null && passwordEncoder.matches(input_password, user.getPassword())) {
            if(!isValidPassword(requestDto.getEditPassword())){
                throw new IllegalArgumentException("하나 이상의 대소문자, 특수문자, 숫자를 포함해 8글자 이상이어야 합니다.");
            }

            if(requestDto.getEditPassword().equals(editPassword)) {
                throw new IllegalArgumentException("현재 비밀번호와 같은 비밀번호로 설정할 수 없습니다.");
            }
            user.setPassword(passwordEncoder.encode(editPassword));
        }
        else if(input_password != null && !passwordEncoder.matches(input_password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if(requestDto.getName() != null)
            user.setName(requestDto.getName());
        if(requestDto.getPhoneNumber() != null)
            user.setPhoneNumber(requestDto.getPhoneNumber());
        if(requestDto.getEmail() != null)
            user.setEmail(requestDto.getEmail());
        if(requestDto.getNickname() != null)
            user.setNickname(requestDto.getNickname());
        if(requestDto.getBio() != null)
            user.setBio(requestDto.getBio());
        if(requestDto.getBirthday() != null)
            user.setBirthday(requestDto.getBirthday());
        return new ResponseUserDto(user);
    }

    public static boolean isValidPassword(String password) {
        return pattern.matcher(password).matches();
    }
}
