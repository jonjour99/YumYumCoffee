package com.yum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yum.domain.BranchDTO;

@Mapper
public interface BranchMapper {
	
	public List<BranchDTO> selectBranchList();
	
	
	
}




