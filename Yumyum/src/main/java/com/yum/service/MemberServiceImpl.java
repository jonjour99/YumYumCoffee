package com.yum.service;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yum.domain.MemberDTO;
import com.yum.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public MemberDTO login(String id, String pw) { 
		
		return memberMapper.login(id, pw);
	}
	
	@Override
	public int registerMember(String name, String id, String pw, String tel, String email, String birth) {
		int queryResult = 0;
		
	   	String encodePw = passwordEncoder.encode(pw);
		queryResult = memberMapper.insertMember(name, id, encodePw, tel, email, birth);
		
		        
		return queryResult;
	}
	@Override
	public int updateMemInfo(MemberDTO params) {
		int queryResult = 0;
		
		if (params.getUserNum() != 0) {
			queryResult = memberMapper.updateMember(params);
		}
		
		return queryResult;
	}
	
	@Override
	public MemberDTO getMemberDetail(Long userNum) {
		return memberMapper.selectMemberDetail(userNum);
	}
	
	@Override
	public boolean deleteMember(Long userNum) {
		int queryResult = 0;
		
		MemberDTO member = memberMapper.selectMemberDetail(userNum);
		
		if (member !=null && "N".equals(member.getDeleteYn())) {
			queryResult = memberMapper.deleteMember(userNum);
		}
		
		return (queryResult == 1) ? true : false;
	}
	@Override
	public List<MemberDTO> getMemberList() {
		List<MemberDTO> memberList = Collections.emptyList();
		
		int memberTotalCount = memberMapper.selectMemberTotalCount();
		
		if (memberTotalCount > 0) {
			memberList = memberMapper.selectMemberList();
		}
		
		return memberList;
	}
	@Override
	public int idOverlapCheck(String id) {
		int result = memberMapper.idOverlapCheck(id);
		
		return result;
	}
	@Override
	public String findId(String name, String email) {
		String result = memberMapper.findId(name, email);
		
		return result;
	}
	@Override
	public int findPw(String id, String email) {
		int result = memberMapper.findPw(id, email);
		
		return result;
	}

	@Override
	public void updatePw(String pw, String id) {

		String newPw = passwordEncoder.encode(pw);
		memberMapper.updatePw(newPw, id);
	}
	
	
}
