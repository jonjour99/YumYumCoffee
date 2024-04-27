package com.yum.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yum.constant.SessionConstants;
import com.yum.domain.CartDTO;
import com.yum.domain.MemberDTO;
import com.yum.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	// 장바구니에 지점명
	@GetMapping(value = "orderproduct")
	public String getCart (Model model, HttpSession session ) {
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);
		Long branchNum = Long.valueOf(1);
		model.addAttribute("branchNum", branchNum);
	
		return "/order/orderTemp";
		
	}
	
	
	// 추가 기능
	@PostMapping("/oderPage")
	@ResponseBody
	public String insertCart(CartDTO cartdto, HttpSession session) {
		// 로그인 체크
//		HttpSession session = request.getSession();
//		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
//		if(memberDTO == null) {
//			return "5";
//		} 
		
		// 카트 등록
	//	int add = cartService.insertCart(cartdto);
		return "";
//		return add + "";
	}
	
	
//	// 리스팅 기능
//	@GetMapping(value = "orderPage")
//	public String getCart (Model model ) {
//		
//		CartDTO dto = new CartDTO();
//		List<CartDTO> list = cartService.getCartList(dto);
//		model.addAttribute("list",list);
//		return "/order/orderPage";
//		
//	}
	
	// 삭제 기능
	@PostMapping("/orderPage/delete")
	public String deleteCart(CartDTO cartdto) {
		cartService.deleteCart(cartdto);
		return "redirect:/orderPage" + cartdto;
	}
	


}
