package com.yum.domain;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends CommonDTO {
	
	/** 상품 번호 */	
	private Long productNum;
	
	/** 상품코드 */
	private String codeId;
	
	/** 상품이름 */
	private String name;
	
	/** 상품 가격 */
	private int price;
	
	/** 상품정보 */
	private String info;
	
	/**-----------------------------*/
	/** 파일 변경 여부 */
	private String changeYn;
	
	/** 파일 인덱스 리스트 */
	private List<Long> fileIdxs;
	
	
	/*------------------------------------------*/
	/* 이미지 경로 주문 페이지 이미지 경로 불러올떄 필요함*/    
	private String imgPath;
	
}
