package com.hanghae.thiscord_clone.controller;

import com.hanghae.thiscord_clone.Dto.request.LoginRequestDto;
import com.hanghae.thiscord_clone.Dto.request.SignUpRequestDto;
import com.hanghae.thiscord_clone.Dto.response.LoginResponseDto;
import com.hanghae.thiscord_clone.Dto.response.MsgResponseDto;
import com.hanghae.thiscord_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public MsgResponseDto signup(@RequestBody @Valid SignUpRequestDto signUpRequestDto){

        return userService.signup(signUpRequestDto);
    }

    @PostMapping("/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto,response);
    }
}
