package com.cos.iter.domain.user;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class UserRoleTest {
	
	@Test
	public void userRoleTest() {
		log.info(UserRole.ADMIN.getKey());
		log.info(UserRole.ADMIN);
		User user = User.builder()
				.role(UserRole.ADMIN)
				.build();
		log.info(user.getRole());
	}
}
