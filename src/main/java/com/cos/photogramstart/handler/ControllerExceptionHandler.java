package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController // 낚아챈 데이터
@ControllerAdvice // 모든 exception을 낚아챔
public class ControllerExceptionHandler {

	// validation 오류는 이거나 밑에꺼 쓰고
	@ExceptionHandler(CustomValidationException.class)
	public String vaildationException(CustomValidationException e) {
		// CMRespDto, Script 비교
		// 1.클라이언트에게 응답할때는 Script 좋음
		// 2.Ajax통신 - CMRespDto
		// 3.Android통신 - CMRespDto
		return Script.back(e.getErrorMap().toString());
		//return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());
	}
	
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> vaildationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	
	// 그 외의 모든 오류는 이걸 쓰면 될듯
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
}
