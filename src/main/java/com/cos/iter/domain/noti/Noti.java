package com.cos.iter.domain.noti;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.iter.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Noti {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	private NotiType notiType;
	
	@ManyToOne
	@JoinColumn(name="from_user_id", foreignKey = @ForeignKey(name="FK_NOTIFICATION_FROM_USER_ID"))
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_user_id", foreignKey = @ForeignKey(name="FK_NOTIFICATION_TO_USER_ID"))
	private User toUser;

	@CreationTimestamp
	private LocalDateTime createDate;
}
