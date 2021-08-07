package com.cos.iter.config.hanlder;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.iter.config.hanlder.ex.MyImageIdNotFoundException;
import com.cos.iter.config.hanlder.ex.MyUserIdNotFoundException;
import com.cos.iter.config.hanlder.ex.MyUsernameNotFoundException;
import com.cos.iter.util.Script;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value=MyUserIdNotFoundException.class)
	public String myUserIdNotFoundException(Exception e) {
		return Script.back(e.getMessage());
	}
	
	@ExceptionHandler(value=MyUsernameNotFoundException.class)
	public String myUsernameNotFoundException(Exception e) {
		return Script.alert(e.getMessage());
	}
	
	@ExceptionHandler(value=MyImageIdNotFoundException.class)
	public String myImageIdNotFoundException(Exception e) {
		return Script.alert(e.getMessage());
	}
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public String myIllegalArgumentException(Exception e) {
		return Script.alert(e.getMessage());
	}
}
