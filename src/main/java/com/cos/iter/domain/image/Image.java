package com.cos.iter.domain.image;

import javax.persistence.*;

import com.cos.iter.domain.post.Post;

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
	@JoinColumn(name="post_id", foreignKey = @ForeignKey(name="FK_IMAGE_POST_ID"))
	private Post post;

	@Column(columnDefinition = "smallint default 0")
	private short sequence;
}





