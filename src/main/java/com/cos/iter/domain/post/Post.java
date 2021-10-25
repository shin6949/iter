package com.cos.iter.domain.post;

import com.cos.iter.domain.comment.Comment;
import com.cos.iter.domain.like.Like;
import com.cos.iter.domain.tag.Tag;
import com.cos.iter.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String mapImageUrl;

    // Image를 select하면 여러개의 Tag가 딸려옴.
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY) //연관관계 주인의 변수명을 적는다.
    @JsonIgnoreProperties({"post"}) //Jackson한테 내리는 명령
    private List<Tag> tags;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @Transient
    private int likeCount;

    @Transient
    private boolean likeState;
}
