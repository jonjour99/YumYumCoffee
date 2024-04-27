package com.yum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yum.domain.CouponDTO;
import com.yum.domain.MemberDTO;
import com.yum.domain.OrderHistoryDTO;

@Mapper
public interface MypageMapper {
	//쿠폰 추가
	public int insertCoupon();
	
	// 회원 쿠폰 리스트 가져오기
	public List<CouponDTO> selectCouponList(CouponDTO params); //회원번호
	
	// 쿠폰 삭제
	public int deleteCoupon(CouponDTO params);
	
	// 회원 상세 정보 가져오기
	public MemberDTO selectUserDetail(int userNum);
	
	// 쿠폰 갯수
	public int countCoupon(CouponDTO Params);
	
	// 과거 주문 내역 가져오기
	public List<OrderHistoryDTO> selectOrderHistory(
				@Param("userNum") int userNum
				, @Param("period") int period
				, @Param("firstIndex") int firstIndex
				);
	
	// 과거 주문 내역 존재 확인
	public int countOrder(
				@Param("userNum") int userNum
				, @Param("period") int period
			);
	
	// 회원 정보 확인
	public MemberDTO identification(@Param("pw")String pw, @Param("id")String id);


	
}
