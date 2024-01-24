package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDatails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController { 
	
	private final ImageService imageService;

	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping({"/image/popular"})
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}
	
	// 사용자에게 데이터를 받고 service에게 호출 해주면 된다.
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDatails principalDatails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null);
		}
		
		// 서비스 호출
		imageService.사진업로드(imageUploadDto, principalDatails);
		
		return "redirect:/user/"+principalDatails.getUser().getId(); // 업로드를 딱 누르면 /user/{?}로 오게
	}
}
