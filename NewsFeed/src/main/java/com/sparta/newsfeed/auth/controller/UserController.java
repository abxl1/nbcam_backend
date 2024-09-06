package com.sparta.newsfeed.auth.controller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.auth.dto.UserRequestDto;
import com.sparta.newsfeed.auth.jwt.JwtUtil;
import com.sparta.newsfeed.auth.service.UserService;
import com.sparta.newsfeed.profile.dto.RequestUserDto;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ResponseUserDto> signup(@RequestBody RequestUserDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserRequestDto requestDto, HttpServletResponse response) {
        try {
            String bearerToken = userService.login(requestDto); // 사용자 정보 확인
            jwtUtil.addJwtToCookie(bearerToken, response);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("사용자를 찾을 수 없습니다.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
            } else if (e.getMessage().equals("비밀번호가 일치하지 않습니다.")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request, 컴파일 에러 때문에 넣음.
    }

    @GetMapping("/withdrawal") // 회원 탈퇴
    public ResponseEntity<Void> withdrawal(@Auth AuthUser authUser) {
        userService.withdrawal(authUser);
        return ResponseEntity.ok().build();
    }
}
