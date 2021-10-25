package com.cos.iter.domain.like;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	@CreationTimestamp
	private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Post image;

    public Post getImage() {
        return image;
    }

    public void setImage(Post image) {
        this.image = image;
    }
}


