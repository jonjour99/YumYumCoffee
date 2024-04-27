package com.yum.service;

import java.util.List;

import com.yum.domain.BranchDTO;
import com.yum.domain.ImgDTO;

public interface BranchService {
	public List<BranchDTO> getBranchList();

	public ImgDTO getImg(Long imgNum);

}
