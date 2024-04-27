package com.yum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yum.domain.CouponDTO;
import com.yum.service.MypageService;

@SpringBootTest
public class CouponTests {
	
	@Autowired
	MypageService mypageService;
	
	
//	쿠폰리스트 가져오기/성공
	@Test
	public void getCouponList() {
		int userNum = 2;
		CouponDTO params=new CouponDTO();
		params.setUserNum(userNum);
		mypageService.getCouponList(params);
		System.out.println("쿠폰 테스트 완료");
	}
	
	
//	 쿠폰갯수 테스트/성공
	 @Test
	 public void testOfCountCouponService() {
		int userNum = 2;
		CouponDTO params=new CouponDTO();
		params.setUserNum(userNum);
		System.out.println(mypageService.countCoupon(params));
	}
	 
//	주문 이력 조회
	 @Test
	 public void testOfSelectOrderHistory() {
		 int userNum = 2;
		 int period = 30;
//		 System.out.println(mypageService.getOrderHistory(userNum, period,1,5));
	 }

}
