package com.yum.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDTO {
	
	/** 지점번호 (PK) */
	private Long branchNum;

	/** 지점명 */
	private String branchName;
	
	/** 주소 */
	private String address;

	/** 위도 */
	private double latitude;
	
	/** 경도 */
	private double longitude;
	
	/** 전화번호 */
	private String telephone;
	
	/** 지점장번호 */
	private Long managerNum;
	
}
