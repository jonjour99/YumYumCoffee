package com.yum.service;

import com.yum.domain.MemberDTO;

public interface EmailService {
	
	//회원가입 시 이메일 인증코드 전송
	public void sendSimpleMessage(String to)throws Exception;
	//비밀번호 찾기 시 임시비밀번호 전송
	public void sendNewPW(String to)throws Exception;
	
}
