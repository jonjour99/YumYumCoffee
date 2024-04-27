package com.yum.service;

import java.util.List;

import com.yum.domain.CartDTO;
import com.yum.domain.OrderDTO;
import com.yum.domain.PaymentDTO;

public interface OrderService {
	
	// 주문 내역 추가
	public boolean insertOrder(OrderDTO params);
	
	//최근 생성한 주문 컬럼의 주문번호 가져오기
	public Long getOrderNum(Long userNum);
	
	// 주문 세부 내역 추가
	public boolean insertOrderDetail(CartDTO params);
	
	// 쿠폰 추가
	public boolean insertCoupon(Long userNum, Long totalQty);
	
	// 결제 정보 추가
	public boolean inserstPayInfo(PaymentDTO params);
	
	// 결제 정보에 주문 번호 추가
	public boolean addOrderNumPay(PaymentDTO params);
	
}
