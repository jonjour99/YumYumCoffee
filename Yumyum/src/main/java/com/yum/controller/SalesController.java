package com.yum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yum.domain.SalesDTO;
import com.yum.service.SalesService;

@Controller
public class SalesController {
	
	@Autowired
	private SalesService salesService;
	
	@GetMapping(value="/totalSales")
	public String openSalesList(@ModelAttribute("params") SalesDTO params, Model model) {
		List<SalesDTO> salesList=salesService.getSalesList(params);
		model.addAttribute("salesList",salesList);
		return "sales/totalSales";
	}

}
