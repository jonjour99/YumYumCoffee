package com.yum.service;

import java.util.List;

import com.yum.domain.CartDTO;

public interface CartService {

	// 장바구니 추가
	public boolean insertCart(CartDTO params);
	
	// 장바구니 리스팅
	public List<CartDTO> getCartList(Long userNum, Long branchNum);
	
	// 장바구니 수량 업데이트
	public boolean updateCartQty(CartDTO params);
	
	// 장바구니 삭제
	public boolean deleteCart(CartDTO params);
	
}