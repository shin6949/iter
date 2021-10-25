package com.cos.iter.domain.noti;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotiRepository extends JpaRepository<Noti, Integer> {
	@Modifying
	@Query(value = "INSERT INTO noti(from_user_id, to_user_id, noti_type, create_date) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(int fromUserId, int toUserId, String notiType);
	
	List<Noti> findByToUserId(int loginUserId);
}
