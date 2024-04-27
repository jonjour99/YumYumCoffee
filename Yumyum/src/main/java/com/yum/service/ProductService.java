package com.yum.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yum.domain.BranchProductDTO;
import com.yum.domain.ImgDTO;
import com.yum.domain.ProductDTO;


public interface ProductService {
	
	public boolean registerProduct(ProductDTO params);
	
	public boolean registerProduct(ProductDTO params, MultipartFile[] files);
	
	public ProductDTO getProductDetail(Long productNum);

	public void deleteProduct(Long productNum);

	public List<ProductDTO> getProductList(ProductDTO params);
	
	public List<ImgDTO> getAttachFileList(Long productNum);
	
	//branchProduct
	public List<BranchProductDTO> getBranchProductList(int userNum,BranchProductDTO params);
	
	public void updateBProduct(BranchProductDTO params);
	
	
	
}
