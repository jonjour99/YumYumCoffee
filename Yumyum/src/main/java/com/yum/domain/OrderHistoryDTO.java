package com.yum.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderHistoryDTO {
//	주문번호
	int orderNum;
//	회원번호
	int userNum;
//	주문일
	LocalDateTime orderTime;
//	지점명
	String branchName;
//	총금액	
	int totalPrice;
//	개수
	int qty;
//	가격
	int price;
//	커스텀
	String info;
//	제품이름
	String name;
}
