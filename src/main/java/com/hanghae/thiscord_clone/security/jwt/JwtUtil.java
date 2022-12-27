package com.hanghae.thiscord_clone.security.jwt;

import com.hanghae.thiscord_clone.exception.custom.CustomSecurityException;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import com.hanghae.thiscord_clone.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

	private final UserDetailsServiceImpl userDetailsService;
	public static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 1440;  // 24시간


	@Value("${jwt.secret.key}")
	private String secretKey;

	private Key key;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String resolveToken(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtUtil.AUTHORIZATION_HEADER)) {
			return bearerToken.substring(7);
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

	public String generateToken(Authentication authentication) {
		Date accessTokenExpiresIn = new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME);

		return TOKEN_PREFIX + Jwts.builder()
			.setSubject(authentication.getName())
			.setExpiration(accessTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}

