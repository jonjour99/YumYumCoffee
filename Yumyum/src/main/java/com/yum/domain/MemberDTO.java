package com.yum.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MemberDTO {
	
//	회원번호
	int userNum;

//	id
	String id;

//	이름
	String name;
//	비밀번호
	String pw;
//	전화번호
	String tel;
//	email
	String email;
//	생년월일
	String birth;
//	관리자 권한
	int authority;
//	스탬프
	String stamp;
//	탈퇴
	String deleteYn;
	
}
