package com.yum.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.yum.constant.SessionConstants;
import com.yum.domain.MemberDTO;
import com.yum.service.EmailService;
//import com.yum.service.EmailService;
import com.yum.service.MemberService;


@Controller
/* @RequestMapping("/yum") */
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping(value = "/home")
	public String home(HttpServletRequest request, Model model,
			@SessionAttribute(name = SessionConstants.loginMember, required = false) MemberDTO loginMember) {
		
		model.addAttribute("member", loginMember);
		System.out.println(request);
		System.out.println(model);
		
		return "Home/homePage";
	}
	
	@GetMapping(value = "/login")
	public String getLogin() {
		
		return "login/login";
	}
	
	@PostMapping(value = "/login")
	public String doLogin(@ModelAttribute MemberDTO params, Model model, HttpSession session, 
							HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("로그인222");
		MemberDTO member = memberService.login(params.getId(), params.getPw());
		System.out.println(member);
		
		if(member == null) {
			
			String failID = "failID";
			model.addAttribute("noID", failID);
			System.out.println("noID");
			
			return "login/login";
		}
		
		//MemberDTO login = memberService.login(params, session);
        logger.info("전달받은 pw:"+member.getPw());
        logger.info("matches pw:"+params.getPw());
		boolean pwCheck = passwordEncoder.matches(params.getPw(), member.getPw());
        logger.info("pwCheck:"+pwCheck);
        
		if(member != null && pwCheck) { 
			
			session.setAttribute(SessionConstants.loginMember, member);
			model.addAttribute("member", member);
			System.out.println("loginS");
			
			return "redirect:/home"; 
			
		} else {
			
			System.out.println("loginF");
			String failPW = "failPW";
			model.addAttribute("failPW", failPW);
			
			return "login/login";
		}
	}
	
	@PostMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    
	    if (session != null) {
	        session.invalidate();   // 세션 날림
	    }

		return "redirect:/home"; 
	}

	@GetMapping(value = "/register")
	public String openRegister() {

		return "login/register";
	}
	
	@PostMapping(value = "/register")
	public String registerBoard(HttpSession session,
			@RequestParam("name") String name, @RequestParam("id") String id, 
			@RequestParam("pw") String pw, @RequestParam("tel") String tel,
			@RequestParam("email") String email, @RequestParam("birth") String birth) {
		
		try {
			int isRegistered = memberService.registerMember(name, id, pw, tel, email, birth);
			if (isRegistered == 0) {
				logger.info("isRegistered: "+isRegistered);
				return "redirect:/register";
				// TODO => 회원 등록에 실패하였다는 메시지를 전달
				
			} else if (isRegistered == 1) {
//				회원가입
				logger.info("isRegistered: "+isRegistered);
				return "redirect:/login";
				
			} 
			
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/login";
	}
	
	@PostMapping(value = "/quitMember")
	public String doQuitMember(HttpServletRequest request, HttpSession session,
						@RequestParam(value = "userNum", required = false) Long userNum) {

        logger.info("doQuitMember 진입");
        if (userNum == null) {
			System.out.println("userNum: "+userNum);
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/quit";
		}

		try {
			boolean isDeleted = memberService.deleteMember(userNum);
			if (isDeleted == false) {
				logger.info("isDeleted: "+isDeleted);
				// TODO => 회원 삭제에 실패하였다는 메시지를 전달
			}
			logger.info("isDeleted: "+isDeleted);
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}
		
	    session = request.getSession(false);
	    
	    if (session != null) {
	        session.invalidate();   // 세션 날림
	    }
		return "redirect:/home";
	}
	
	@ResponseBody
	@PostMapping(value = "/idOverlapCheck")
	public int idOverlapCheck(@RequestParam("id") String id) {
		int result=0;
		
		try {
	    //    logger.info("idOverlapCheck 진입");
	    //    logger.info("전달받은 id:"+id);
			result = memberService.idOverlapCheck(id); // 중복확인한 값을 int로 받음
	    //    logger.info("확인 결과:"+result);
	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	@GetMapping(value = "/findId")
	public String pageFindId() {
		
		return "login/findId";
	}
	@ResponseBody
	@PostMapping(value = "/findId")
	public String findId(@RequestParam("name") String name, @RequestParam("email") String email) {
		
		String result = "";
			try {
				result = memberService.findId(name, email); // 확인한 값을 String로 받음
				//model.addAttribute("userID", params.getId());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = "";
			}
		
		return result;
	}
	

	@GetMapping(value = "/findPw")
	public String pageFindPw() {
		
		return "login/findPW";
	}
	@ResponseBody
	@PostMapping(value = "/findPw")
	public int findPw(@RequestParam("id") String id, @RequestParam("email") String email) {
		
		int result = 0;
		
			try {
				result = memberService.findPw(id, email); // 확인한 값을 int로 받음
				//model.addAttribute("userID", params.getId());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = 0;
			}
		
		return result;
	}

	@ResponseBody
	@PostMapping(value = "/updatePw")
	public String updatePw(@RequestParam("pw") String pw,
			@SessionAttribute(name = SessionConstants.loginMember, required = false) MemberDTO loginMember, HttpSession session) {
		
		String result = "";
			try {
				memberService.updatePw(pw, loginMember.getId());
				result = pw;
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = "";
			}
		
		return result;
	}
}
