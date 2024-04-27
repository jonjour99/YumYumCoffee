package com.yum.mapper;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yum.domain.MemberDTO;

@Mapper
public interface MemberMapper {
	
	public String findId(@Param("name")String name, @Param("email")String email);
	
	public int findPw(@Param("id")String id, @Param("email")String email);
	
	public void updatePw(@Param("pw")String pw, @Param("id")String id);
	
	public int idOverlapCheck(String id);
	
	public MemberDTO login(@Param("id") String id, @Param("pw") String pw);
	
	public int insertMember(@Param("name")String name, @Param("id") String id, @Param("pw") String pw, 
			@Param("tel")String tel, @Param("email")String email, @Param("birth")String birth);
	
	public MemberDTO selectMemberDetail(Long userNum);
	
	public int updateMember(MemberDTO params);
	
	public int deleteMember(Long userNum);
	
	public List<MemberDTO> selectMemberList();

	public int selectMemberTotalCount();
}
