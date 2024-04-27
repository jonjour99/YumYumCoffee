package com.yum.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.yum.constant.SessionConstants;
import com.yum.domain.CouponDTO;
import com.yum.domain.MemberDTO;
import com.yum.service.MemberService;
import com.yum.service.MypageService;

@Controller
public class MypageController {
	
	@Autowired
	private MypageService mypageService;
	@Autowired
	private MemberService memberService;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);

//회원탈퇴
	@GetMapping(value="/quit")
	public String quitMember(Model model, HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
        logger.info("탈퇴화면 member 세션: "+member);
		model.addAttribute("member", member);	
		model.addAttribute("ID", member.getId());	
		
		return "mypage/quitMember";
	}
	
//정보수정페이지 이동시 뜨는 모달
	@ResponseBody
	@PostMapping(value="/identification")
	public int identification(@ModelAttribute MemberDTO params, HttpSession session,
			@SessionAttribute(name = SessionConstants.loginMember, required = false) MemberDTO loginMember) {
		
		MemberDTO member = mypageService.identification(params.getPw(),loginMember.getId());
		int result=0;
		
		try {
	        logger.info("identification 진입");
	        logger.info("전달받은 pw:"+member.getPw());
	        logger.info("matches pw:"+params.getPw());
			boolean pwCheck = passwordEncoder.matches(params.getPw(), member.getPw());
	        logger.info("pwCheck:"+pwCheck);
			
	        logger.info("세션에서 가져오는 id:"+loginMember.getId());
	        //result = mypageService.identification(pw,loginMember.getId());

			if(member != null && pwCheck) { 
				result = 1;
		        logger.info("확인 결과:"+result);
			} else {
				result = 0;
		        logger.info("확인 결과:"+result);
			}
	        
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			result=0;
			System.out.println(e.getMessage());
		}
		return result;
	}
	
//	마이페이지 회원정보 불러오기
	@GetMapping(value="/mypage")
	public String openMypage(Model model, HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member",member);
		CouponDTO params = new CouponDTO();
		params.setUserNum(member.getUserNum());
		int couponTotalCount = mypageService.countCoupon(params);
		model.addAttribute("couponTotalCount",couponTotalCount);
		
		return "mypage/mypage";
	}	

	
//	마이페이지 >> 내 정보 수정
	@GetMapping(value="/updateuser")
	public String updateuser(Model model, HttpSession session) {
//		@SessionAttribute(name = SessionConstants.loginMember, required = false) MemberDTO loginMember
//		HttpSession session
//		MemberDTO member = mypageService.getUserDetail(loginMember.getUserNum());
//		이부분 수정 >> 파라미터 (HttpSession session)
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);		
		
		return "mypage/updateUser";
	}
	
	@PostMapping(value = "/updateMemInfo")
	public String updateMemInfo(Model model, HttpSession session, final MemberDTO params) {
		
		try {
			int isUpdated = memberService.updateMemInfo(params);
			if (isUpdated == 0) {
				logger.info("isUpdated: "+isUpdated);
				return "redirect:/mypage";
				// TODO => 회원 등록에 실패하였다는 메시지를 전달
				
			} else if (isUpdated == 1) {
//			마이페이지 >> 내 정보 수정
				MemberDTO member = memberService.getMemberDetail(Long.valueOf(params.getUserNum()));
				session.setAttribute(SessionConstants.loginMember, member);
				logger.info("isUpdated: "+isUpdated);
				return "redirect:/mypage";
				
			} 
			
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/mypage";
	}
		
//	마이페이지 >> 과거 주문 내역 페이지
	@GetMapping(value="/orderhistory")
	public String orderhistory(Model model, HttpSession session) {
		
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);		
		
		return "mypage/orderHistory";
	}
	
}
