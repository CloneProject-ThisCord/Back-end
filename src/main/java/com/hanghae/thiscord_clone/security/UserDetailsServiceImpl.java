package com.hanghae.thiscord_clone.security;

import com.hanghae.thiscord_clone.entity.User;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import com.hanghae.thiscord_clone.exception.custom.UserException;
import com.hanghae.thiscord_clone.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new UserException(ErrorCode.USER_NOT_FOUND));

        return new UserDetailsImpl(user, user.getEmail());
    }
}