package com.cos.iter.domain.user;

import com.cos.iter.web.dto.FollowRespDto;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@SqlResultSetMapping(
		name = "FollowRespDtoMapping",
		classes = @ConstructorResult(
				targetClass = FollowRespDto.class,
				columns = {
						@ColumnResult(name="id", type = Integer.class),
						@ColumnResult(name="username", type = String.class),
						@ColumnResult(name="name", type = String.class),
						@ColumnResult(name="profile_image", type = String.class),
						@ColumnResult(name="follow_state", type = Boolean.class),
						@ColumnResult(name="equal_user_state", type = Boolean.class),
				}
		)
)
@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String name;
	private String bio;

	private String profileImage;
	@Transient
	private String url;
	private Boolean gender;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@CreationTimestamp
	private LocalDateTime createDate;

	public String getProfileImage() {
		final String blogStorageUrl = System.getenv("AZURE_BLOB_URL");

		return blogStorageUrl + "/profile/" + profileImage;
	}
}
