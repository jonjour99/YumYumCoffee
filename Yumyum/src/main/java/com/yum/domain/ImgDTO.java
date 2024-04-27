package com.yum.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgDTO {

	/** 파일 번호 (PK) */
	private Long imgNum;

	/** 게시글 번호 (FK) */
	private Long productNum;

	/** 원본 파일명 */
	private String originalName;
	
	/** 저장 파일명 */
	private String saveName;
	
	/** 파일경로 */
	private String imgPath;
	
	/** 입력 날짜 */
	private Date insertTime;

	/** 파일 크기 */
	private long size;
	
	/** 삭제여부 */
	private String deleteYn;

}

