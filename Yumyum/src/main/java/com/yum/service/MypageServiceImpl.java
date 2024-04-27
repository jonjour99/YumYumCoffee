package com.yum.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yum.domain.CartDTO;
import com.yum.domain.CouponDTO;
import com.yum.domain.MemberDTO;
import com.yum.domain.OrderHistoryDTO;
import com.yum.mapper.MypageMapper;

@Service
public class MypageServiceImpl implements MypageService {
	
	@Autowired
	private MypageMapper mypageMapper;
		
	// 회원 쿠폰 리스트 가져오기
	@Override
	public List<CouponDTO> getCouponList(CouponDTO params){
		List<CouponDTO> couponList = Collections.emptyList();
		int couponTotalCount = mypageMapper.countCoupon(params);
		if (couponTotalCount > 0) {
			try {
				couponList = mypageMapper.selectCouponList(params);
			} catch(RuntimeException e) {
				System.out.println(e);
			} catch (Exception e){
				System.out.println(e);
			}
		}
		return couponList;
	}
	
	//쿠폰 삭제
	@Override
	public boolean deleteCoupon(CouponDTO params) {
		int queryResult = 0;
			queryResult = mypageMapper.deleteCoupon(params);
		return (queryResult == 1) ? true : false;
		
	}
	
	
	// 회원 상세 정보 가져오기
	@Override
	public MemberDTO getUserDetail(int userNum) {
		return mypageMapper.selectUserDetail(userNum);
	}
	
	
	// 회원 쿠폰 갯수 카운트 
	@Override
	public int countCoupon(CouponDTO params) {
		int couponTotalCount = mypageMapper.countCoupon(params);
		return couponTotalCount;
	}

	
	// 과거 주문 내역 가져오기
	@Override
	public List<OrderHistoryDTO> getOrderHistory(int userNum, int period, int firstIndex){
//		public List<OrderHistoryDTO> getOrderHistory(OrderHistoryDTO params){
		List<OrderHistoryDTO> orderHistoryList = Collections.emptyList();
		int orderTotalCount = mypageMapper.countOrder(userNum, period);
		if (orderTotalCount > 0) {
			try {
				orderHistoryList = mypageMapper.selectOrderHistory(userNum, period, firstIndex);
			} catch(RuntimeException e) {
				System.out.println(e);
			} catch (Exception e){
				System.out.println(e);
			}
			
		}
		return orderHistoryList;
	}

	// 과거 주문 내역 확인
	@Override
	public int countOrder(int userNum, int period) {
		int orderTotalCount = mypageMapper.countOrder(userNum, period);
		return orderTotalCount;
	}
	
	// 회원 정보 확인
	@Override
	public MemberDTO identification(String pw, String id) {
		return mypageMapper.identification(pw,id);
	}
}
