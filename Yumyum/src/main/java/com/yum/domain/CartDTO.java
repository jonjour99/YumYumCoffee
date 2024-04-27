package com.yum.domain;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class CartDTO {
	
	// 회원번호
	private Long userNum;
	// 제품번호 
	private Long productNum;
	// 지점번호
	private Long branchNum;
	// 커스텀여부
	private Long shotCustom;
	
	// 개수
	private Long qty;
	// 총 금액
	private Long totalPrice;
	
	// 제품명
	private String name;
	// 제품금액
	private Long price;
	// 제품 이미지 경로
	private String imgPath;

	// 주문번호
	private Long orderNum;
}