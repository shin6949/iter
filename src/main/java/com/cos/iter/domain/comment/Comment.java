package com.cos.iter.domain.comment;

import javax.persistence.*;

import com.cos.iter.domain.post.Post;
import com.cos.iter.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;

	@ManyToOne
	@JoinColumn(name="post_id", foreignKey = @ForeignKey(name="FK_COMMENT_POST_ID"))
	private Post post;
	
	// 수정
	@ManyToOne
	@JoinColumn(name="user_id", foreignKey = @ForeignKey(name="FK_COMMENT_USER_ID"))
	private User user;

	@Transient
	private boolean commentHost;
}



