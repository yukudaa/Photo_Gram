package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDatails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Value("${file.path}")
	private String uploadFolder; // application.yml의 값 가져오기
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDatails principalDatails) {
		UUID uuid = UUID.randomUUID(); // uuid
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 실제 file 이름이 들어감 1.jpg
		System.out.println("이미지 파일이름: "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName); // 경로 + file명
		
		// 통신, I/O -> 예외가 발생할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDatails.getUser()); // 5cf6237d-c404-43e5-836b-e55413ed0e49_bag.jpeg
		Image imageEntity = imageRepository.save(image);
		
		System.out.println(imageEntity);
	}
}
