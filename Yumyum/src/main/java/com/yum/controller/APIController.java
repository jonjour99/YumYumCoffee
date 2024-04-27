package com.yum.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yum.adapter.GsonLocalDateTimeAdapter;
import com.yum.domain.CartDTO;
import com.yum.domain.CouponDTO;
import com.yum.domain.OrderHistoryDTO;
import com.yum.service.CartService;
import com.yum.service.MypageService;

@RestController
public class APIController {

	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private CartService cartService;
	
//유저번호에 따른 쿠폰 리스트 불러오기
	@GetMapping(value = "yumyum/coupon/{userNum}")
	public JsonObject getCouponList(
			@PathVariable("userNum") int userNum
			, @ModelAttribute("params") CouponDTO params
			, HttpSession session) {
		
		JsonObject jsonObj = new JsonObject();

		List<CouponDTO> couponList = mypageService.getCouponList(params);
		if (CollectionUtils.isEmpty(couponList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(couponList).getAsJsonArray();
			jsonObj.add("couponList", jsonArr);
		}
		return jsonObj;
	}

//	유저별, 기간 별, 과거 주문 내역 확인 (기본값 7일(7일/31일/전체), 주문 5개)
	@GetMapping(value = "yumyum/orderhistory/{userNum}/{period}/{firstIndex}")
	public JsonObject getOrderHistoryList(
				@PathVariable("userNum") int userNum
				, @PathVariable("period") int period
				, @PathVariable("firstIndex") int firstIndex
				, HttpSession session
				) {
		
		JsonObject jsonObj = new JsonObject();
		List<OrderHistoryDTO> orderHistoryList = mypageService.getOrderHistory(userNum, period, firstIndex);
		if (CollectionUtils.isEmpty(orderHistoryList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(orderHistoryList).getAsJsonArray();
			jsonObj.add("orderHistoryList", jsonArr);
		}
		return jsonObj;
	}
	
	//유저번호, 지점번호에 따른 장바구니 리스트 불러오기
		@GetMapping(value = "yumyum/cart/{userNum}/{branchNum}")
		public JsonObject getCommentList(
				@PathVariable("userNum") Long userNum
				, @PathVariable("branchNum") Long branchNum
				, HttpSession session) {
			
			JsonObject jsonObj = new JsonObject();

			List<CartDTO> cartList = cartService.getCartList(userNum, branchNum);
			if (CollectionUtils.isEmpty(cartList) == false) {
				Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
				JsonArray jsonArr = gson.toJsonTree(cartList).getAsJsonArray();
				jsonObj.add("cartList", jsonArr);
			}
			return jsonObj;
		}
	
//		장바구니 제품 수량 변경
		@RequestMapping(value = { "/yumyum/cart/update", "/yumyum/cart/update/{shotCustom}/{userNum}/{branchNum}/{productNum}/{qty}" }, method = { RequestMethod.POST, RequestMethod.PATCH })
		public JsonObject updateCartQty(
				@PathVariable(value = "shotCustom", required = false) Long shotCustom
				, @PathVariable(value = "userNum", required = false) Long userNum
				, @PathVariable(value = "branchNum", required = false) Long branchNum
				, @PathVariable(value = "productNum", required = false) Long productNum
				, @PathVariable(value = "qty", required = false) Long qty
				, HttpSession session
				) {

			JsonObject jsonObj = new JsonObject();
			final CartDTO params = new CartDTO();

			try {
				if ((shotCustom != null) && (userNum != null) && (branchNum != null) && (productNum != null) &&(qty != null)) {
					params.setShotCustom(shotCustom);
					params.setUserNum(userNum);
					params.setBranchNum(branchNum);
					params.setProductNum(productNum);
					params.setQty(qty);
					
					boolean isUpdated =cartService.updateCartQty(params);
					jsonObj.addProperty("result", isUpdated);
				}
			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			}
			return jsonObj;
		}
		
//		장바구니 제품 삭제
		@RequestMapping(value = { "/yumyum/cart/delete", "/yumyum/cart/delete/{shotCustom}/{userNum}/{branchNum}/{productNum}" }, method = { RequestMethod.POST, RequestMethod.DELETE })
		public JsonObject updateCartQty(
				@PathVariable(value = "shotCustom", required = false) Long shotCustom
				, @PathVariable(value = "userNum", required = false) Long userNum
				, @PathVariable(value = "branchNum", required = false) Long branchNum
				, @PathVariable(value = "productNum", required = false) Long productNum
				, HttpSession session
				) {

			JsonObject jsonObj = new JsonObject();
			final CartDTO params = new CartDTO();

			try {
				if ((shotCustom != null) && (userNum != null) && (branchNum != null) && (productNum != null)) {
					params.setShotCustom(shotCustom);
					params.setUserNum(userNum);
					params.setBranchNum(branchNum);
					params.setProductNum(productNum);
					
					boolean isDeleted =cartService.deleteCart(params);
					jsonObj.addProperty("result", isDeleted);
				}
			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			}
			return jsonObj;
		}
	

}