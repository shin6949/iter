package com.cos.iter.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// JpaRepository가 extends 되면 @Repository 필요 없음. IoC자동으로 됨.
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	
	@Query(value = "select U.*, (select true from follow where from_user_id = ?2 and to_user_id = u.id) as matpal from follow f inner join user u on f.to_user_id = u.id and f.from_user_id = ?1", nativeQuery = true)
	List<User> mFollowingUser(int pageUserId, int loginUserId);
	
	@Query(value = "select U.*,(select true from follow where from_user_id = ?2 and to_user_id = u.id) as matpal from follow f inner join user u on f.from_user_id = u.id and f.to_user_id = ?1", nativeQuery = true)
	List<User> mFollowerUser(int pageUserId, int loginUserId);
}
