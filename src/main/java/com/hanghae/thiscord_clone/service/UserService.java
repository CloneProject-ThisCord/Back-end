package com.hanghae.thiscord_clone.service;


import com.hanghae.thiscord_clone.domain.ProfileImages;
import com.hanghae.thiscord_clone.domain.User;
import com.hanghae.thiscord_clone.dto.request.LoginRequestDto;
import com.hanghae.thiscord_clone.dto.request.SignUpRequestDto;
import com.hanghae.thiscord_clone.dto.response.MsgResponseDto;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import com.hanghae.thiscord_clone.exception.custom.UserException;
import com.hanghae.thiscord_clone.repository.UserRepository;
import com.hanghae.thiscord_clone.security.jwt.JwtUtil;
import com.hanghae.thiscord_clone.util.UserUtil;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtUtil jwtUtil;

	public ResponseEntity<?> signup(SignUpRequestDto requestDto) {
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new UserException(ErrorCode.DUP_USER_EMAIL);
		}

		// 고유 해시태그 번호(4자리) 생성(랜덤)
		Random random = new Random();
		String hashtag = "#";
		while (isPresentHashtag(hashtag) != null) {
			for (int i = 0; i < 4; i++) {
				hashtag += random.nextInt((9) + 1);
			}
		}

		User user = User.builder()
						.email(requestDto.getEmail())
						.username(requestDto.getUsername())
						.password(requestDto.getPassword())
						.profilePic(ProfileImages.getProfileImage(hashtag.indexOf(3)))
						.hashTag(hashtag)
						.build();
		userRepository.saveAndFlush(user);

		return ResponseEntity.ok(new MsgResponseDto("회원가입 성공", HttpStatus.OK.value()));
	}

	public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
		//인증 전 객체
		UsernamePasswordAuthenticationToken beforeAuthentication
			= new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

		//인증 완료된 객체
		Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);

		//JWT 생성
		httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.generateToken(afterAuthentication));

		UserUtil.getCurrentUser().setStatus(true);

		return ResponseEntity.ok(new MsgResponseDto("로그인 성공", HttpStatus.OK.value()));
	}

	public ResponseEntity<?> logout() {
		User user = UserUtil.getCurrentUser();
		user.setStatus(false);
		return ResponseEntity.ok(new MsgResponseDto("로그아웃 성공", HttpStatus.OK.value()));
	}

	private User isPresentHashtag(String hashtag) {
		return userRepository.findByHashTag(hashtag).orElse(null);
	}
}
