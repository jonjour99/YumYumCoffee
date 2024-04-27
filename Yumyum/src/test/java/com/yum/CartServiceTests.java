package com.yum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yum.domain.CartDTO;
import com.yum.service.CartService;

@SpringBootTest
public class CartServiceTests {
	
	@Autowired
	private CartService cartService;
	
	//등록 테스트
//		@Test
//		public void addCartTest() {
//			//given
//				int userNum = 33;
//				int productNum = 22;
//				int qty = 5;
//				
//				CartDTO dto = new CartDTO(); 
//				dto.setUserNum(userNum);
//				dto.setProductNum(productNum);
//				dto.setQty(qty);
//			
//			//when
//				int result = cartService.insertCart(dto);
//			
//			//then
//				System.out.println("** result : " + result);
//			
//			
//		}

}
