package com.yum.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yum.domain.CouponDTO;
import com.yum.domain.MemberDTO;
import com.yum.service.MypageService;


 
@RestController
public class ReactController {
	
//--------------------------마이페이지------------------------------------- 
	
	@Autowired
	private MypageService mypageService;
	
//	마이페이지 회원정보 불러오기
	@GetMapping(value="/react/mypage")
	public Map<String, Object> openMypage() {
	    Map<String, Object> map = new HashMap<String, Object>();
		int userNum=1; //회원번호
		MemberDTO user = mypageService.getUserDetail(userNum);
		map.put("user",user);
		
		CouponDTO params = new CouponDTO();
		params.setUserNum(userNum);
		int couponTotalCount = mypageService.countCoupon(params);
		map.put("couponTotalCount",couponTotalCount);
		return map;
	}
	
//	마이페이지 >> 과거 주문 내역 페이지
	@GetMapping(value="react/orderhistory")
	public Map<String, Object> orderHistory(@RequestParam(value = "userNum", required = true) int userNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberDTO user = mypageService.getUserDetail(userNum);
		map.put("user", user);		
		return map;
	}
	
	
 
}
