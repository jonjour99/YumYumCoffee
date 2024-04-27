package com.yum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yum.domain.CartDTO;
import com.yum.domain.OrderDTO;
import com.yum.domain.PaymentDTO;

@Mapper
public interface OrderMapper {
	
	// 주문 내역 추가
	public int insertOrder(OrderDTO params);
	
	// 최근 생성한 주문 컬럼의 주문번호 가져오기
	public Long selectOrderNum(Long userNum);
	
	// 주문 세부 내역 추가
	public int insertOrderDetail(CartDTO params);
	
	// 주문한 수량만큼 스탬프 추가
	public int insertCoupon(@Param("userNum") Long userNum, @Param("totalQty") Long totalQty);
	
	// 결제 정보 추가
	public int insertPayInfo(PaymentDTO params);
	
	// 결제 정보에 주문 번호 추가
	public int updateOrderNumPay(PaymentDTO params);
}
