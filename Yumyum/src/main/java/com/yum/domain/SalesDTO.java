package com.yum.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDTO {
	
	//분류기준
	String point;
	
	//주문일
	LocalDateTime orderTime;
	
	//합계
	int sum;
	
	//월별 별명
	String month;

}
