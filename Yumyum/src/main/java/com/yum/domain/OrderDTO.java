package com.yum.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

// 주문 정보
public class OrderDTO {
	
	// 고객 번호
	private Long userNum;
	// 지접 번호
	private Long branchNum;
	// 픽업 여부
	private String pickupYn;
	// 픽업 시간
	private String pickupTime;
	// 주문 시간
	private String orderTime;
	// 결제 금액(총 금액)
	private Long totalPrice;
	
}
