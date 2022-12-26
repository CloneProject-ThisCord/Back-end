package com.hanghae.thiscord_clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.thiscord_clone.util.PasswordEncConverter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@Column(nullable = false)
	@Convert(converter = PasswordEncConverter.class)
	private String password;

	@Column(nullable = false)
	private String profilePic;

	@Column(nullable = false)
	private String hashTag;

	//로그인 여부
	private boolean status;

	@JsonIgnore
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RoomUser> roomList;

	@Builder
	private User (String email, String username, String password, String profilePic, String hashTag) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.profilePic = profilePic;
		this.hashTag = hashTag;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
