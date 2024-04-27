package com.yum.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//결제 정보
public class PaymentDTO {
	
	// 주문 번호
	private Long orderNum;
	// 아임포트 고유 결제번호
	private String impUid;
	// 가맹점에서 생성/관리하는 고유 주문번호
	private String merchantUid;
	// 결제수단
	private String payMethod;
	// 결제금액
	private Long paidAmount;
	// 카드사 승인번호(신용카드결제에 한하여 제공)
	private String applyNum;
	
}
