package com.cos.controller.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.controller.demo.web.domain.User;

@RestController
public class HttpResponseJsonController {

	@GetMapping("/resp/json")
	public String respJson() {
		return "{\"username\":\"cos\"}";
	}
	
	@GetMapping("/resp/json/object")
	public String respJsonObject() {
		User user = new User();
		user.setUsername("홍길동");
		
		String data = "{\"username\":\""+user.getUsername()+"\"}"; // 내가 Json으로 만든것은 미친짓
		return data;
	}
	
	@GetMapping("/resp/json/javaobject")
	public User respJsonJavaObject() {
		User user = new User();
		user.setUsername("홍길동");
		
		return user; // 1. MessageConverter가 자동으로 JavaObject를 Json(구:xml)으로 변경해서 통신을 통해 응답을 해준다.
					 // 2. @RestController 일때만 MessageConverter가 작동한다.
	}
}
