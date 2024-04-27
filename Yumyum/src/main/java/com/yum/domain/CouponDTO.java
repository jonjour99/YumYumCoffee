package com.yum.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDTO {
	
//	쿠폰번호
	private int cpNum;
//	회원번호
	private int userNum;
//	결제번호, 사용후 업데이트
	private int pmNum;
//	발급일
	private LocalDateTime insertDate;
//	만료일
	private LocalDateTime expirationDate;
//	쿠폰 종류
	private String kind;
//	사용여부
	private String deleteYn;
//	설명
	private String description;
//  쿠폰을 사용한 주문 번호
	private Long orderNum;
		
}
