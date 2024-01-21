package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 -> 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨 (localhost:8080 -> localhost:8080/login 을 비활성화)
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated() // 인증이 필요
			.anyRequest().permitAll() // 그 외는 허용
			.and()
			.formLogin()
			.loginPage("/auth/signin") // 인증이 필요한 url이면 이쪽으로 자동으로 가게 하겠다
			.defaultSuccessUrl("/"); // 로그인이 정상적으로 처리되면 / 로 가게 할게
	}
}
