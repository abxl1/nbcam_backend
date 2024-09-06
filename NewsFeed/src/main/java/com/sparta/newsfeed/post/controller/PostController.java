package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.post.dto.postDto.PostRequestDto;
import com.sparta.newsfeed.post.dto.postDto.PostResponseDto;
import com.sparta.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto postRequestDto,
                                                    @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.savePost(postRequestDto, userId));
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<PostResponseDto> likePost(@PathVariable Long id, @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.likePost(id, userId));
    }

    @DeleteMapping("/posts/{postsid}/like/{likeid}")
    public void deleteLike(@PathVariable Long postsid,
                           @PathVariable Long likeid,
                           AuthUser authUser) {
        Long userId = authUser.getId();
        postService.deleteLike(postsid, likeid, userId);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPostList() {
        return ResponseEntity.ok(postService.getPostList());
    }

    @GetMapping("/posts/news")
    public ResponseEntity<Page<PostResponseDto>> getPost(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.getPost(page, size, userId));
    }

    @GetMapping("/posts/news/modified")
    public ResponseEntity<Page<PostResponseDto>> getPostByModifiedAt(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.getPostByModifiedAt(page, size, userId));
    }

    @GetMapping("/posts/news/like")
    public ResponseEntity<Page<PostResponseDto>> getPostByLike(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.getPostByLike(page, size, userId));
    }

    @GetMapping("/posts/news/search")
    public ResponseEntity<Page<PostResponseDto>> getPostByTime(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                               @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.getPostByTime(startDate, endDate, page, size, userId));
    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostRequestDto postRequestDto,
                                                      @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        return ResponseEntity.ok(postService.updatePost(id, postRequestDto, userId));
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id, @Auth AuthUser authUser) {
        Long userId = authUser.getId();
        postService.deletePost(id, userId);
    }


}
