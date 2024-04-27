package com.yum.service;

import java.util.List;

import com.yum.domain.SalesDTO;

public interface SalesService {
	
	public List<SalesDTO> getSalesList(SalesDTO params);

}
