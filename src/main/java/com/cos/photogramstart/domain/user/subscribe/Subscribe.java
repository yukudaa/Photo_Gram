package com.cos.photogramstart.domain.user.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity // DB에 테이블을 생성
@Data
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Table(
	uniqueConstraints = {
			@UniqueConstraint(
					name="subscribe_kr",
					columnNames = {"fromUserId","toUserId"}
					
					)
	}
) // 유니크
public class Subscribe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	@JoinColumn(name="formUserId") // 이렇게 컬럼명 만들어! 니 말대로 만들지 말고
	@ManyToOne
	private User fromUser;
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser;
	
	private LocalDateTime createDate;
	
	@PrePersist // DB에 Insert가 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
