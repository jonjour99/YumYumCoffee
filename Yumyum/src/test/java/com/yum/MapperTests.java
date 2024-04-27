package com.yum;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yum.domain.MemberDTO;
import com.yum.mapper.MemberMapper;
import com.yum.mapper.MypageMapper;

@SpringBootTest
public class MapperTests {
	
	@Autowired
	private MypageMapper mypageMapper;
	@Autowired
	private MemberMapper registerMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	@DisplayName("패스워드 암호화 테스트")
	void passwordEncode() {
	   // given
	   String rawPassword = "12345678";
     // when
	   String encodedPassword = passwordEncoder.encode(rawPassword);
     // then
    	assertAll(
	         () -> assertNotEquals(rawPassword, encodedPassword),
	         () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
	   );
	}
	
	 @Test
	 public void testOfSelectOrderHistory() {
		 int userNum = 2;
		 int period = 30;
		 System.out.println(mypageMapper.selectOrderHistory(userNum, period, 1));
	 }

	
//	@Test
//	public void testRegisterMapper() {
//		MemberDTO params = new MemberDTO();
//		params.setName("김종주");
//		params.setId("jongju");
//		params.setPw("jong123");
//		params.setTel("010-8012-7716");
//		params.setEmail("jongjoo15@gmail.com");
//		params.setBirth("1999-11-16");
//		
//		int result = registerMapper.insertMember(params);
//		System.out.println("결과는 " + result + "입니다.");
//	}
//	@Test
//	public void testMultipleInsert() {
//		for (int i = 2; i <= 50; i++) {
//			MemberDTO params = new MemberDTO();
//			params.setName(i+" 이름");
//			params.setId(i+" id");
//			params.setPw(i+" pw");
//			params.setTel(i+" 8210");
//			params.setEmail(i+" email");
//			params.setBirth(i+" 996");
//
//			registerMapper.insertMember(params);
//		}
//	}
	
	@Test
	public void testOfSelectDetail() {
		MemberDTO member = registerMapper.selectMemberDetail((long) 1);
		try {
			//String boardJson = new ObjectMapper().writeValueAsString(board);
            String memberJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(member);

			System.out.println("=========================");
			System.out.println(memberJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		MemberDTO params = new MemberDTO();
		params.setName("김아무개");
		params.setId("amugae");
		params.setPw("amugae123");
		params.setTel("010-1234-5678");
		params.setEmail("amugae@gmail.com");
		params.setBirth("1990-12-31");
		params.setUserNum((int) 2);

		int result = registerMapper.updateMember(params);
		if (result == 2) {
			MemberDTO member = registerMapper.selectMemberDetail((long) 2);
			try {
				//String boardJson = new ObjectMapper().writeValueAsString(board);
                String memberJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(member);

				System.out.println("=========================");
				System.out.println(memberJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
//				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testOfDelete() {
		int result = registerMapper.deleteMember((long) 50);
		if (result == 50) {
			MemberDTO member = registerMapper.selectMemberDetail((long) 50);
			try {
				//String boardJson = new ObjectMapper().writeValueAsString(board);
                String memberJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(member);

				System.out.println("=========================");
				System.out.println(memberJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
//				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
//	@Test
//	public void testSelectList() {
//		int memberTotalCount = registerMapper.selectMemberTotalCount();
//		if (memberTotalCount > 0) {
//			List<MemberDTO> memberList = registerMapper.selectMemberList();
//			if (CollectionUtils.isEmpty(memberList) == false) {
//				for (MemberDTO member : memberList) {
//					System.out.println("=========================");
//					System.out.println(member.getName());
//					System.out.println(member.getId());
//					System.out.println(member.getPw());
//					System.out.println(member.getTel());
//					System.out.println(member.getEmail());
//					System.out.println(member.getBirth());
//					System.out.println("=========================");
//				}
//			}
//		}
	}
	

	
//	쿠폰 발급 테스트/성공
//	@Test
//	public void testOf() {
//		System.out.println(mypageMapper.insertCoupon());
//	}
//	
//	mypageMapper 쿠폰리스트 가져오기/성공
//	@Test
//	public void testOfSelectCouponDetail() {
//		CouponDTO params = new CouponDTO();
//		int userNum = 2;
//		params.setUserNum(userNum);
//		List<CouponDTO> couponList = mypageMapper.selectCouponList(params);
//		if (CollectionUtils.isEmpty(couponList) == false) {
//			for (CouponDTO coupon : couponList) {
//				System.out.println("=========================");
//				System.out.println(coupon.getKind());
//				System.out.println(coupon.getDescription());
//				System.out.println(coupon.getExpirationDate());
//				System.out.println("=========================");
//			}
//		}
//	}
	
	
//	 mapper쿠폰갯수 테스트/성공
//	 @Test
//	 public void testOfCountCouponMapper() {
//		int userNum = 2;
//		CouponDTO params=new CouponDTO();
//		params.setUserNum(userNum);
//		System.out.println(mypageMapper.countCoupon(params));
//	}
	

	
	
	
//	회원정보 불러오기/성공
//	@Test
//	public void testOfSelectDetail() {
//		int userNum=2;
//		UserDTO user = mypageMapper.selectUserDetail(userNum);
//		try {
//	        String userJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(user);
//	
//			System.out.println("=========================");
//			System.out.println(userJson);
//			System.out.println("=========================");
//	
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	@Test
//	public void testOfInsert() {
//		CouponDTO params = new CouponDTO();
//		params.setUserNum(50000); 
//		params.setPmNum(60000);
//		params.setKind("스탬프");
//		try {
//		int result = CouponMapper.insertStampCoupon(params);
//		System.out.println("결과는 " + result + "입니다.");
//		} catch(RuntimeException e) {
//			System.out.println(1);
//			System.out.println(e);
//		} catch(Exception e) {
//			System.out.println(2);
//			System.out.println(e);
//		}
//		System.out.println("결과는 " + result + "입니다.");
//	}
	