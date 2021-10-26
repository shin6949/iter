package com.cos.iter.domain.follow;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import com.cos.iter.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "follow")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="from_user_id", foreignKey = @ForeignKey(name="FK_FOLLOW_FROM_USER_ID"))
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_user_id", foreignKey = @ForeignKey(name="FK_FOLLOW_TO_USER_ID"))
	private User toUser;
	
	@CreationTimestamp
	private Timestamp createDate;
}






