package com.yum.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
//	회원번호
	int userNum;
//	id
	String id;
//	이름
	String name;
//	전화번호
	String tel;
//	email
	String email;
//	생년월일
	String birth;
//	관리자 권한
	int authority;
//	스탬프
	int stamp;
//	탈퇴
	String deleteYn;		
}
