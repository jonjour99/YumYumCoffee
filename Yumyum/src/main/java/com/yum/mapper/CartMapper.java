package com.yum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yum.domain.CartDTO;


@Mapper
public interface CartMapper {

	// 제품 추가
	public int insertCart(CartDTO params);
	
	// 제품 수량 수정
	public int updateCartQty(CartDTO params);
	
	// 장바구니에 제품이 있는 상태에서 제품 추가
	public int addCart(CartDTO params);

	// 제품이 장바구니에 있는지 확인
	public CartDTO selectCart(CartDTO params);

	// 제품 삭제
	public int deleteCart(CartDTO params);
	
	// 장바구니 목록 
	public List<CartDTO> selectCartList(
			@Param("userNum") Long userNum
			, @Param("branchNum") Long branchNum);
	
	// 장바구니 내부 목록 갯수
	public Long countTotalCart(
			@Param("userNum") Long userNum
			, @Param("branchNum") Long branchNum);
	
	// 제품이 장바구니에 있는지 확인 
	public CartDTO checkCart(CartDTO params);
	// 선택한 지점명 가져오기
	public String selectBranchName(Long branchNum);
	
}
