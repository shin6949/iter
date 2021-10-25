package com.cos.iter.domain.user;

import com.cos.iter.web.dto.FollowRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	private String name;
	private String website;
	private String bio;
	private String phone;
	private Boolean gender;
	private String profileImage;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@CreationTimestamp
	private Timestamp createDate;

}
