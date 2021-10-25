package com.cos.iter.domain.comment;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cos.iter.domain.post.Post;
import org.hibernate.annotations.CreationTimestamp;

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
	@JoinColumn(name="post_id")
	private Post post;
	
	// 수정
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@Transient
	private boolean commentHost;
}



