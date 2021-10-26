package com.cos.iter.domain.like;

import java.sql.Timestamp;

import javax.persistence.*;

import com.cos.iter.domain.post.Post;
import org.hibernate.annotations.CreationTimestamp;

import com.cos.iter.domain.image.Image;
import com.cos.iter.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "likes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="FK_LIKES_USER_ID"))
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id", foreignKey = @ForeignKey(name="FK_LIKE_POST_ID"))
	private Post post;
	
	@CreationTimestamp
	private Timestamp createDate;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}


