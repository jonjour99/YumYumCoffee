package com.yum.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yum.domain.SalesDTO;
import com.yum.mapper.SalesMapper;

@Service
public class SalesServiceImpl implements SalesService {
	
	@Autowired
	private SalesMapper salesMapper;

	@Override
	public List<SalesDTO> getSalesList(SalesDTO params) {
		List<SalesDTO> salesList=Collections.emptyList();
		
		int salesTotalCount=salesMapper.selectSalesTotalCount(params);
		
		if(salesTotalCount >0) {
			salesList=salesMapper.selectSales(params);
		}
		return salesList;
	}

}
