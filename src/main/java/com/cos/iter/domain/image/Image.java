package com.cos.iter.domain.image;

import javax.persistence.*;

import com.cos.iter.domain.post.Post;

import lombok.*;

@Entity(name = "image")
@Data
//@Getter
//@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"post"})
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





