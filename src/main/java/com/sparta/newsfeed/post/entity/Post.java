package com.sparta.newsfeed.post.entity;

import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.post.fix.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "contents")
    private String contents;

    @Column(name = "likes")
    private Long likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    public Long countLikes() {
        this.likes = (long) this.likesList.size();
        return this.likes;
    }

    public Post(String contents, String imgUrl, User user) {
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.user = user;
    }

    public void update(String contents, String imgUrl) {
        this.contents = contents;
        this.imgUrl = imgUrl;
    }
}
