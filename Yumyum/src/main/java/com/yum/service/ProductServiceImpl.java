package com.yum.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yum.domain.BranchProductDTO;
import com.yum.domain.ImgDTO;
import com.yum.domain.ProductDTO;
import com.yum.mapper.BranchProductMapper;
import com.yum.mapper.ImgMapper;
import com.yum.mapper.ProductMapper;
import com.yum.paging.PaginationInfo;
import com.yum.util.FileUtils;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ImgMapper imgMapper;
	
	@Autowired
	private BranchProductMapper BPMapper;
	
	@Autowired
	private FileUtils fileUtils;

	@Override
	public boolean registerProduct(ProductDTO params) {
		
		int queryResult = 0;

		if (params.getProductNum() == null) {
			int ProductNumMax = productMapper.selectProductMax(params);
			params.setProductNum((long)ProductNumMax+1);
			queryResult = productMapper.insertProduct(params);
			//List<BranchProductDTO> BPList = BPMapper.selectBranchProductList( params.getProductNum());
			
		} else {
			queryResult = productMapper.updateProduct(params);

			// 파일이 추가, 삭제, 변경된 경우
			if ("Y".equals(params.getChangeYn())) {
				imgMapper.deleteAttach(params.getProductNum());
				// fileIdxs에 포함된 idx를 가지는 파일의 삭제여부를 'N'으로 업데이트
				if (CollectionUtils.isEmpty(params.getFileIdxs()) == false) {
					System.out.println("---------------update문 실행 전----------------------------------");
					System.out.println("params.getFileIdxs()"+params.getFileIdxs());
					imgMapper.updateAttach(params.getFileIdxs());
					System.out.println("----------------update 완료------------------------------------------");
				}

			}
		}

		return (queryResult > 0);
		
	}
	
	@Override
	public boolean registerProduct(ProductDTO params, MultipartFile[] files) {
		int queryResult =1;
		
		if(registerProduct(params) == false) {
			return false;
		}
		
		List<ImgDTO> fileList = fileUtils.uploadFiles(files, params.getProductNum());
		
		if (CollectionUtils.isEmpty(fileList) == false) {
			System.out.println("fileList : "+fileList);
			queryResult = imgMapper.insertAttach(fileList);
			if (queryResult < 1) {
				queryResult = 0;
			}
		}
		return (queryResult > 0);
	}
	

	@Override
	public ProductDTO getProductDetail(Long productNum) {
		return productMapper.selectProductDetail(productNum);
	}

	@Override
	public void deleteProduct(Long productNum) {
		productMapper.deleteProduct(productNum);
	}

	@Override
	public List<ProductDTO> getProductList(ProductDTO params) {
		List<ProductDTO> productList =Collections.emptyList();

		int productTotalCount =productMapper.selectProductTotalCount(params);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(productTotalCount);
		params.setPaginationInfo(paginationInfo);		
		
		if(productTotalCount >0) {
			productList = productMapper.selectProductList(params);
			
			if(productList!=null) {
				
				productList.get(0).setTotalRecordCount(productTotalCount);//총 데이터 수
				productList.get(0).setTotalPageCount(paginationInfo.getTotalPageCount());//전체 페이지 갯수
				productList.get(0).setCurrentPageNo(paginationInfo.getCriteria().getCurrentPageNo()); // 현재 페이지 번호
				productList.get(0).setRecordsPerPage(paginationInfo.getCriteria().getRecordsPerPage());	//페이지당 나타낼 글 수				
			}
			
		}
		
	//		/** 전체 데이터 개수 */
	//		private int totalRecordCount;
	//
	//		/** 전체 페이지 개수 */
	//		private int totalPageCount;
			return productList;
			
	}
	
	@Override
	public List<ImgDTO> getAttachFileList(Long productNum) {

		int fileTotalCount = imgMapper.selectAttachTotalCount(productNum);
		if (fileTotalCount < 1) {
			return Collections.emptyList();
		}
		return imgMapper.selectAttachList(productNum);
	}

	//BranchProduct관련
	@Override
	public List<BranchProductDTO> getBranchProductList(int userNum,BranchProductDTO params) {
		List<BranchProductDTO> BPList=Collections.emptyList();
		params.setBranchNum((long)userNum);
		int BproductTotalCount=BPMapper.selectBProductTotalCount(userNum);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(BproductTotalCount);
		
		System.out.println("------------------BproductTotalCount:"+BproductTotalCount+"---------");
		
		params.setPaginationInfo(paginationInfo);
		
		System.out.println("------------paginationInfo: "+paginationInfo+"---------");
		System.out.println("userNum : "+userNum);
		
		if(BproductTotalCount >0) {
			BPList = BPMapper.selectBPList(params);
		}
		
		return BPList;
	}
	

	@Override
	public void updateBProduct(BranchProductDTO params) {
		BPMapper.updateBProductState(params);
	}

	
	
	

	


}
