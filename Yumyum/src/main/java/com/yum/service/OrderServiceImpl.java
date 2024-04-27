package com.yum.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yum.domain.CartDTO;
import com.yum.domain.OrderDTO;
import com.yum.domain.PaymentDTO;
import com.yum.mapper.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper paymentMapper;
	
	// 주문 내역 추가
	@Override		
	public boolean insertOrder(OrderDTO params) {
		int queryResult = 0;
			queryResult = paymentMapper.insertOrder(params);
		return (queryResult == 1) ? true : false;
	}
	
	
	//최근 생성한 주문 컬럼의 주문번호 가져오기
	public Long getOrderNum(Long userNum) {
		Long orderNum = paymentMapper.selectOrderNum(userNum);
		return orderNum;
	}
	
	
	// 주문 세부 내역 추가
	@Override		
	public boolean insertOrderDetail(CartDTO params) {
		int queryResult = 0;
			queryResult = paymentMapper.insertOrderDetail(params);
		return (queryResult == 1) ? true : false;
	}
	
	
	// 쿠폰 추가
	public boolean insertCoupon(Long userNum, Long totalQty) {
		int queryResult = 0;
		queryResult = paymentMapper.insertCoupon(userNum, totalQty);
		return (queryResult == 1) ? true : false;
	}
	
	
	// 결제 정보 추가
	public boolean inserstPayInfo(PaymentDTO params) {
		int queryResult = 0;
		queryResult = paymentMapper.insertPayInfo(params);
		return (queryResult == 1) ? true : false;
	}
	
	// 결제 정보에 주문 번호 추가
	public boolean addOrderNumPay(PaymentDTO params) {
		int queryResult = 0;
		queryResult = paymentMapper.updateOrderNumPay(params);
		return (queryResult == 1) ? true : false;
	}
}
