package com.cos.iter.domain.post;

import com.cos.iter.domain.comment.Comment;
import com.cos.iter.domain.image.Image;
import com.cos.iter.domain.like.Like;
import com.cos.iter.domain.tag.Tag;
import com.cos.iter.domain.user.User;
import com.cos.iter.web.dto.UserProfilePostRespDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SqlResultSetMapping(
        name = "UserProfilePostRespDtoMapping",
        classes = @ConstructorResult(
                targetClass = UserProfilePostRespDto.class,
                columns = {
                        @ColumnResult(name="id", type = Integer.class),
                        @ColumnResult(name="image_url", type=String.class),
                        @ColumnResult(name="like_count", type = Integer.class),
                        @ColumnResult(name="comment_count", type = Integer.class)
                }
        )
)
@Entity(name = "post")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"tags", "images", "comments", "likes"})
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
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="FK_POST_USER_ID"))
    private User user;

    private String mapImageUrl;

    // Image를 select하면 여러개의 Tag가 딸려옴.
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY) // 연관관계 주인의 변수명을 적는다.
    @JsonIgnoreProperties({"post"}) // Jackson한테 내리는 명령
    private List<Tag> tags;

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties({"post"}) // Jackson한테 내리는 명령
    private List<Image> images;

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

    @Transient
    private Image firstImage;

    @Transient
    private String createDateString;

    public String getCreateDateString() {
        this.createDateString = getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
        return createDateString;
    }

    public Image getFirstImage() {
        return getImages().get(0);
    }
}
