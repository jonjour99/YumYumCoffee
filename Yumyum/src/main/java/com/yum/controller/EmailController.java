package com.yum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yum.service.EmailService;
import com.yum.service.EmailServiceImpl;
import com.yum.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {
	
	@Autowired
	private EmailService emailservice;
	@Autowired
	private MemberService memberService;
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@ResponseBody
	@PostMapping("/mail")
	public String emailConfirm(@RequestParam("email") String email) {
		String text = "";
		try {
			
			logger.info("post emailConfirm");
			System.out.println("전달 받은 이메일 : "+email);
			emailservice.sendSimpleMessage(email);	
			text = email;
			
		} catch (Exception e) {
			
			text = "";
            e.printStackTrace();
            System.out.println(e.getMessage());
		}
		return text;
	}
	@ResponseBody
	@PostMapping("/newPwMail")
	public String newPwEmailConfirm(@RequestParam("email") String email, @RequestParam("id") String id) {
		String text = "";
		try {
			
			logger.info("post emailConfirm");
			System.out.println("전달 받은 이메일 : "+email);
			emailservice.sendNewPW(email);	
			memberService.updatePw(EmailServiceImpl.ePw, id);
			text = email;
			
		} catch (Exception e) {
			
			text = "";
            e.printStackTrace();
            System.out.println(e.getMessage());
		}
		return text;
	}

	@ResponseBody
	@PostMapping("/verifyCode")
	public int verifyCode(String code) {
		logger.info("Post verifyCode");
		
		int result = 0;
		System.out.println("code : "+code);
		System.out.println("code match : "+ EmailServiceImpl.ePw.equals(code));
		if(EmailServiceImpl.ePw.equals(code)) {
			result =1;
		}
		
		return result;
	}
}
