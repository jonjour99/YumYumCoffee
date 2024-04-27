package com.yum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TryCatchTest {
	
	@Test
	public void Test() throws Exception {
		System.out.println("test 함수 진입");
		try {
			System.out.println("try");
			throw new Exception(); 
		}catch(Exception e){
			System.out.println("catch");
			e.printStackTrace();
			System.out.println("catch2");
			throw new Exception();
		}
	}
}
