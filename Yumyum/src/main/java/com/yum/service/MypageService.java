package com.yum.service;

import java.util.List;

import com.yum.domain.CouponDTO;
import com.yum.domain.MemberDTO;
import com.yum.domain.OrderHistoryDTO;

public interface MypageService {
//	내정보 수정
//	public boolean Info(SignInDTO params);//회원정보 테이블
	
	// 회원 쿠폰 리스트 가져오기
	public List<CouponDTO> getCouponList(CouponDTO params);
	
	// 쿠폰 삭제
	public boolean deleteCoupon(CouponDTO params);
	
	// 회원 상세 정보 가져오기
	public MemberDTO getUserDetail(int userNum);
	
	// 회원 쿠폰 갯수 카운트
	public int countCoupon(CouponDTO params);
	
	// 과거 주문 내역 가져오기
	public List<OrderHistoryDTO> getOrderHistory(int userNum, int period, int firstIndex);
	
	// 과거 주문 내역 확인
	public int countOrder(int userNum, int period);
	
	// 회원 정보 확인
	public MemberDTO identification(String pw, String id);
}
