package com.hanghae.thiscord_clone.security.jwt;

import com.hanghae.thiscord_clone.exception.custom.CustomSecurityException;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import com.hanghae.thiscord_clone.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

	private final UserDetailsServiceImpl userDetailsService;
	public static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 100000L;  // 24시간


	@Value("${jwt.secret.key}")
	private String secretKey;

	private Key key;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
//		byte[] keyBytes = Decoders.BASE64.decode(secretKey);       //기존 코드
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);   //이게 실행되려나?
		key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			System.out.println("token 값:" + bearerToken.split(" ")[1].trim());
			return bearerToken.split(" ")[1].trim();
		}
		return null;
	}

	public String creatToken(String email) {
		Date date = new Date();

		return TOKEN_PREFIX +
			Jwts.builder()
				.setSubject(email)
//                        .claim(AUTHORIZATION_KEY, role) 사용자 속성 관리..?
				.setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
				.setIssuedAt(date)
				.signWith(key, signatureAlgorithm)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities());
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (SecurityException | MalformedJwtException e) {
			log.error(e + ": 잘못된 JWT 서명입니다.");
			throw new CustomSecurityException(ErrorCode.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			log.error(e + ": 만료된 JWT 토큰입니다.");
			throw new CustomSecurityException(ErrorCode.EXPIRED_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.error(e + ": 지원되지 않는 JWT 토큰입니다.");
			throw new CustomSecurityException(ErrorCode.UNSUPPORTED_TOKEN);
		} catch (IllegalArgumentException e) {
			log.error(e + ": JWT 토큰이 잘못되었습니다.");
			throw new CustomSecurityException(ErrorCode.INVALID_TOKEN);
		}
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}

