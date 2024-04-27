package com.yum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yum.domain.SalesDTO;

@Mapper
public interface SalesMapper {
	
	public List<SalesDTO> selectSales(SalesDTO params);
	
	public int selectSalesTotalCount(SalesDTO params);

}
