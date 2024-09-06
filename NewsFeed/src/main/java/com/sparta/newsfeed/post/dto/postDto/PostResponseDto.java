package com.sparta.newsfeed.post.dto.postDto;

import com.sparta.newsfeed.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final Long id;
    private final Long userId;
    private final String imgUrl;
    private final String contents;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.imgUrl = post.getImgUrl();
        this.contents = post.getContents();
    }


}
