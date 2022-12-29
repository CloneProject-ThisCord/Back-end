package com.hanghae.thiscord_clone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TestController {

	@GetMapping("/user/test")
	public String tokenTest() {
		return "";
	}
}
