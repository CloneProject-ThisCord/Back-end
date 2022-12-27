package com.hanghae.thiscord_clone.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String username;

//	@Convert(converter = PasswordEncConverter.class)
	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String profilePic;

	@Column(nullable = false)
	private String hashTag;

	@Builder
	public User (String email, String username, String password, String profilePic, String hashTag) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.profilePic = profilePic;
		this.hashTag = hashTag;
	}
}
