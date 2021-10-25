package com.cos.iter.domain.image;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import com.cos.iter.domain.post.Post;
import org.hibernate.annotations.CreationTimestamp;

import com.cos.iter.web.dto.UserProfileImageRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SqlResultSetMapping(
		name = "UserProfileImageRespDtoMapping",
		classes = @ConstructorResult(
				targetClass = UserProfileImageRespDto.class,
				columns = {
						@ColumnResult(name="id", type = Integer.class),
						@ColumnResult(name="image_url", type = String.class),
						@ColumnResult(name="like_count", type = Integer.class),
						@ColumnResult(name="comment_count", type = Integer.class)
				}
		)
)
@Entity(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String url;

	private float latitude;

	private float longitude;

	@ManyToOne()
	@JoinColumn(name="post_id")
	private Post post;

	@CreationTimestamp
	private LocalDateTime createDate;

	public String getCreateDateStringPretty() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return simpleDateFormat.format(getCreateDate());
	}
}





