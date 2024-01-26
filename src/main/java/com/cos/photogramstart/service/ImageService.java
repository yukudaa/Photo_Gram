package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDatails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) X
	public Page<Image> 이미지스토리(int principalId,Pageable pageable) {
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		return images;
	}
	
	
	@Value("${file.path}")
	private String uploadFolder; // application.yml의 값 가져오기
	
	@Transactional // 트랜잭션: 일의 최소 단위
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
		imageRepository.save(image);
		
		// System.out.println(imageEntity); // Image와 User 호출 무한반복
	}
}
