package com.hanghae.thiscord_clone.repository;

import com.hanghae.thiscord_clone.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByHashTag(String hashTag);
}
