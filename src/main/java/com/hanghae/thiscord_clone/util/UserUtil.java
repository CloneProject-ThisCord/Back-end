package com.hanghae.thiscord_clone.util;

import com.hanghae.thiscord_clone.domain.User;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import com.hanghae.thiscord_clone.exception.custom.UserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
	public static User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}
		return (User) authentication.getPrincipal();
	}

}
