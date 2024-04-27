package com.yum;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.yum.domain.BranchDTO;
import com.yum.mapper.BranchMapper;

@SpringBootTest
public class BranchMapperTests {
	
	@Autowired
	private BranchMapper branchMapper;
	
	@Test
	public void testSelectList() {
//		int branchTotalCount = branchMapper.selectBoardTotalCount();
		
//		if (branchTotalCount > 0) {}
		
		List<BranchDTO> branchList = branchMapper.selectBranchList();
		if (CollectionUtils.isEmpty(branchList) == false) {
			for (BranchDTO branch : branchList) {
				System.out.println("=========================");
				System.out.println(branch.getBranchName());
				System.out.println(branch.getTelephone());
				System.out.println(branch.getAddress());
				System.out.println("=========================");
			}
		}
	}

}
