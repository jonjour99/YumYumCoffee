package com.yum.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchProductDTO extends CommonDTO{
	
	/** 지점번호 (식별) */
	private Long branchNum;
	
	/** 제품번호 (식별) */
	private Long productNum;
	
	/** 가리기 여부  */
	private String hidenYn;
	
	/** 제품명*/
	private String name;
	
	/** 회원번호 */
	private int userNum;

}
