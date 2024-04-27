package com.yum;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.yum.domain.BranchProductDTO;
import com.yum.mapper.BranchProductMapper;

@SpringBootTest
public class BranchProductMapperTests {
	
	@Autowired
	private BranchProductMapper branchProductMapper;
	
//	@Test
//	public void testSelectList() {
//		int branchTotalCount = branchProductMapper.selectBoardTotalCount();
//		
//		if (branchTotalCount > 0) {}
//		
//		List<BranchProductDTO> branchProductList = branchProductMapper.selectBranchProductList();
//		if (CollectionUtils.isEmpty(branchProductList) == false) {
//			for (BranchProductDTO branch : branchProductList) {
//				System.out.println("=========================");
//				System.out.println(branch.getBranchNum());
//				System.out.println("=========================");
//			}
//		}
//	}

}
