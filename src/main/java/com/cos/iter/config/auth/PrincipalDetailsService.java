package com.cos.iter.config.auth;

import java.util.function.Function;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.iter.config.auth.dto.LoginUser;
import com.cos.iter.domain.user.User;
import com.cos.iter.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final HttpSession session;

	// Security Session > Authentication > UserDetails
	// 해당 함수가 정상적으로 리턴되면 @AuthenticationPricipal 어노테이션 활성화됨.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username)
				.map(new Function<User, User>() {
					@Override
					public User apply(User t) {
						session.setAttribute("loginUser", new LoginUser(t));
						return t;
					}
				})
				.orElse(null);

		if(userEntity == null) {
			throw new UsernameNotFoundException("");
		}
		return new PrincipalDetails(userEntity);
	}

}
