package com.hanghae.thiscord_clone.service;


import com.hanghae.thiscord_clone.Dto.response.LoginResponseDto;
import com.hanghae.thiscord_clone.Dto.response.MsgResponseDto;
import com.hanghae.thiscord_clone.entity.ProfileImages;
import com.hanghae.thiscord_clone.entity.User;
import com.hanghae.thiscord_clone.Dto.request.LoginRequestDto;
import com.hanghae.thiscord_clone.Dto.request.SignUpRequestDto;
//import com.hanghae.thiscord_clone.Dto.response.MsgResponseDto;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import com.hanghae.thiscord_clone.exception.custom.UserException;
import com.hanghae.thiscord_clone.repository.UserRepository;
import com.hanghae.thiscord_clone.security.jwt.JwtUtil;
import com.hanghae.thiscord_clone.util.PasswordEncConverter;
import com.hanghae.thiscord_clone.util.UserUtil;

import java.util.Optional;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	public MsgResponseDto signup(SignUpRequestDto requestDto) {

		String email = requestDto.getEmail();
		String username = requestDto.getUsername();
		String password = passwordEncoder.encode(requestDto.getPassword());

		// 고유 해시태그 번호(4자리) 생성(랜덤)
		Random random = new Random();

		String hashtag = "#";
//		while (isPresentHashtag(hashtag) != null) {
			for (int i = 0; i < 4; i++) {
				hashtag += random.nextInt((9) + 1);
			}
//		}

		String profilePic = ProfileImages.getProfileImage(hashtag.indexOf(3));

		Optional<User> foundId = userRepository.findByEmail(email);
		if (foundId.isPresent()){
			throw new UserException(ErrorCode.DUP_USER_EMAIL);
		}

		User user = new User(email, username, password, profilePic, hashtag);
		userRepository.save(user);

		return new MsgResponseDto("회원가입 완료",HttpStatus.OK.value());
	}

	@Transactional(readOnly = true)
	public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
		String email = loginRequestDto.getEmail();
		String password = loginRequestDto.getPassword();

		//사용자 확인
		User user = userRepository.findByEmail(email).orElseThrow(
				()  -> new UserException(ErrorCode.USER_NOT_FOUND)
		);

		//비밀번호 확인
		if(!passwordEncoder.matches(password, user.getPassword())){
			throw new UserException(ErrorCode.MISMATCH_PASSWORD);
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.creatToken(user.getEmail()));

		return  new LoginResponseDto(user.getEmail(), user.getHashTag(), user.getProfilePic());
	}

//	public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
//		//인증 전 객체
//		UsernamePasswordAuthenticationToken beforeAuthentication
//			= new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
//
//		//인증 완료된 객체
//		Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);
//
//		//JWT 생성
//		httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.generateToken(afterAuthentication));
//
//		UserUtil.getCurrentUser().setStatus(true);
//
//		return ResponseEntity.ok(new com.hanghae.thiscord_clone.Dto.response.MsgResponseDto("로그인 성공", HttpStatus.OK.value()));
//	}

//	public ResponseEntity<?> logout() {
//		User user = UserUtil.getCurrentUser();
//		user.setStatus(false);
//		return ResponseEntity.ok(new com.hanghae.thiscord_clone.Dto.response.MsgResponseDto("로그아웃 성공", HttpStatus.OK.value()));
//	}

	private User isPresentHashtag(String hashtag) {
		return userRepository.findByHashTag(hashtag).orElse(null);
	}
}
