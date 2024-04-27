package com.yum.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yum.constant.SessionConstants;
import com.yum.domain.CartDTO;
import com.yum.domain.CouponDTO;
import com.yum.domain.MemberDTO;
import com.yum.domain.OrderDTO;
import com.yum.domain.PaymentDTO;
import com.yum.service.CartService;
import com.yum.service.MemberService;
import com.yum.service.MypageService;
import com.yum.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MemberService memberService;

//	장바구니에서 선택한 제품/총 금액/총 개수(=param), 회원의 쿠폰 정보를 가져와 화면에 띄우기	
	@PostMapping(value = { "/order" })
	public String openOrderList(@RequestParam Map<String, Object> params, HttpSession session,
			HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {

		MemberDTO member = (MemberDTO) session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);

		// 장바구니에서 체크한 제품 정보, 총 금액, 총 개수
		log.info("cartList : {}", params.toString());

		Long totalQty = Long.valueOf(params.get("totalQty").toString());
		// 결제 총금액 : totalSum > totalPrice
		Long totalPrice = Long.valueOf(params.get("totalSum").toString());
		
		// Object to List<CartDTO>
		ObjectMapper objectMapper = new ObjectMapper();
		List<CartDTO> cartList = objectMapper.readValue(params.get("cartList").toString(),
				new TypeReference<List<CartDTO>>() {
				});
		if(cartList.size()>0) {
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("totalQty", totalQty);
			model.addAttribute("cartList", cartList);

			// 회원이 선택한 지점의 지점명
			String branchName = session.getAttribute("branchName").toString();
			model.addAttribute("branchName", branchName);

			// 로그인한 세션에서 UserNum을 받아와 해당하는 유저가 보유한 쿠폰 저장
			CouponDTO coupon = new CouponDTO();
			coupon.setUserNum(member.getUserNum());
			List<CouponDTO> couponList = mypageService.getCouponList(coupon);
			model.addAttribute("couponList", couponList);
			return "order/order";
		} else {
			Long branchNum = Long.valueOf(session.getAttribute("branchNum").toString());
			return "redirect:/product(branchNum="+branchNum+")";
		}
	}

	
	// 결제 완료
	@PostMapping(value={"/orderconfirm"})
	public String orderConfirm(
			@RequestParam Map<String, Object> params
			, HttpSession session
			, Model model
			, OrderDTO order) throws JsonMappingException, JsonProcessingException {
		log.debug("orderConfirm 함수 진입");
		
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);
		
		// 회원번호
		Long userNum = Long.valueOf(member.getUserNum());
		
		// 장바구니에서 체크한 제품 정보, 총 갯수, 총 금액 픽업시간
		Long totalQty = Long.valueOf(params.get("totalQty").toString()); 
		Long totalPrice = Long.valueOf(params.get("totalPrice").toString()); 
		String pickupYn = params.get("pickupYn").toString();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		// 구매한 제품 목록
		List<CartDTO> cartList = objectMapper.readValue(params.get("cartList").toString(),
			new TypeReference<List<CartDTO>>() {});
		
		// 사용한 쿠폰 목록
		List<CouponDTO> couponList = objectMapper.readValue(params.get("couponList").toString(),
			new TypeReference<List<CouponDTO>>() {});
		
		//결제 내역
		PaymentDTO payment =  new Gson().fromJson(params.get("payment").toString(), PaymentDTO.class);
		String impUid = payment.getImpUid().toString();
		
		// 회원이 선택한 지점의 지점번호 
		Long branchNum = Long.valueOf(session.getAttribute("branchNum").toString());
		
		// 주문 내역 DB 추가
		order.setUserNum(userNum);
		order.setBranchNum(branchNum);
		order.setPickupYn(pickupYn);
		order.setTotalPrice(totalPrice);
		
		String msg="";
		try {
			// 결제 내역 추가
			boolean resultPayment = orderService.inserstPayInfo(payment);
			if (resultPayment==true) {
				
				// 주문 내역 DB 인서트, 트랜젝션
				updatePayInfo(session
						, order
						, userNum
						, payment
						, cartList
						, totalQty
						, couponList
						, impUid);
				msg+="commit";
			}
		} catch (Exception e) {			
			log.debug("예외 처리");
			e.printStackTrace();
			msg+="rollback";
			model.addAttribute("payment",payment);
		}
		model.addAttribute("msg", msg);
		return "order/orderconfirm";
	}
	
	
	// 주문 내역 추가 트랜잭션 
	//예외 발생 시, 롤백 실행
	@Transactional(rollbackFor = Exception.class)
	public void updatePayInfo(HttpSession session
			, OrderDTO order
			, Long userNum
			, PaymentDTO payment
			, List<CartDTO> cartList
			, Long totalQty
			, List<CouponDTO> couponList
			, String impUid
			) throws Exception {
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConstants.loginMember);
		
		try {
			orderService.insertOrder(order);
			log.info("주문 내역 인서트 완료");
			Long orderNum = orderService.getOrderNum(userNum);
				
			// 결제내역에 대해 주문번호 업데이트 
			payment.setOrderNum(orderNum);
			orderService.addOrderNumPay(payment);
			
			
			// 장바구니 제품 주문 세부내역으로 인서트 후, 삭제 
			for(CartDTO cart:cartList) {
				cart.setOrderNum(orderNum);
				boolean resultOrderDetail = orderService.insertOrderDetail(cart);
				log.info("주문 세부 내역 인서트 완료");
				if(resultOrderDetail==true) {
					cartService.deleteCart(cart);
					log.info("장바구니 삭제 완료");
				}
			}
			
			// 구매한 제품 수 만큼 쿠폰 적립
			boolean resultCoupon = orderService.insertCoupon(userNum, totalQty);
			log.info("쿠폰 적립 완료");
			
			// 사용한 쿠폰 삭제
			if (couponList.size()>0) {
				for(CouponDTO coupon:couponList) {
					coupon.setOrderNum(orderNum);
					mypageService.deleteCoupon(coupon);
					log.info("쿠폰 삭제 완료");
				}
			}
		
			// 회원 정보 업데이트 
			member = memberService.getMemberDetail(Long.valueOf(member.getUserNum()));
			session.setAttribute(SessionConstants.loginMember, member);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new Exception();
		}
	}
}
