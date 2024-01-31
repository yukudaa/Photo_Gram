package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuth2DetailsService auth2DetailsService;
	
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 -> 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨 (localhost:8080 ->
		// localhost:8080/login 을 비활성화)
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() // 인증이 필요
				.anyRequest().permitAll() // 그 외는 허용
				.and()
				.formLogin()
				.loginPage("/auth/signin") // 인증이 필요한 url이면 이쪽으로 자동으로 가게 하겠다 // GET
				.loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로레스 진행
				.defaultSuccessUrl("/") // 로그인이 정상적으로 처리되면 / 로 가게 할게
				.and()
				.oauth2Login() // form로그인도 하는데, oauth2로그인도 할거야
				.userInfoEndpoint() // oauth2로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.
				.userService(auth2DetailsService); 
	}
}
