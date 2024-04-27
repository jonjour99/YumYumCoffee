package com.yum.controller;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yum.constant.SessionConstants;
import com.yum.domain.BranchDTO;
import com.yum.domain.CartDTO;
import com.yum.domain.ImgDTO;
import com.yum.domain.MemberDTO;
import com.yum.domain.ProductDTO;
import com.yum.mapper.CartMapper;
import com.yum.service.BranchService;
import com.yum.service.CartService;
import com.yum.service.ProductService;
import com.yum.util.DownloadView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartMapper cartMapper;
	
	
	
	/** 업로드 */
	private final String uploadPath = Paths.get("C:", "Users", System.getProperty("user.name"),"Pictures","yumyum").toString();

	
	/**
	 * 파일 다운로드
	 * @param imgNum
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/fileDownload/{imgNum}")
	public DownloadView fileDownload(@PathVariable Long imgNum , Model model, HttpSession session) throws Exception {			
			MemberDTO  member=(MemberDTO)session.getAttribute(SessionConstants.loginMember);
			ImgDTO imgDTO=branchService.getImg(imgNum);	
			if(imgDTO==null) {
				throw new RuntimeException("해당 파일 존재 하지 않습니다.");
			}			
			log.info(" uploadPath+imgDTO.getImgPath()  =>  {}", uploadPath+File.separator+imgDTO.getSaveName());
			File file = new File(uploadPath+File.separator+imgDTO.getSaveName());
			model.addAttribute("downloadFile", file);
			model.addAttribute("orignalName", imgDTO.getOriginalName());
			DownloadView downloadView=new DownloadView();
			return downloadView;			
	}
	
//	주문할 지점 선택
	@GetMapping(value = "/yumyum/branch")
	public String openBranchList(Model model, HttpSession session 
			) {
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);  
		List<BranchDTO> branchList = branchService.getBranchList();
		model.addAttribute("branchList", branchList);
		return "branch/branch";
	}
	

	
//	선택한 지점에 대해 가리기가 적용된 제품 목록
	@GetMapping(value = "/map")
	public String viewMap(@RequestParam(value = "branchNum", required = false) Long branchNum, Model model, HttpSession session) {
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);	
		model.addAttribute("branchNum", branchNum);
		if (branchNum == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 지점 리스트로 리다이렉트
			return "redirect:/yumyum/branch";
		}
		
		return "branch/map";
	}

// 선택한 지점의 제품 페이지 및 장바구니 
	@GetMapping(value = "/product")
	public String openproductList( 
			@RequestParam(value = "branchNum", required = false) Long branchNum
			, Model model, HttpSession session) {
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConstants.loginMember);
		model.addAttribute("member", member);	

		
		if (branchNum == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 지점 리스트로 리다이렉트
			return "redirect:/yumyum/branch";
		} else {
			//선택한 지점명/번호 
			String branchName = cartMapper.selectBranchName(branchNum);
			session.setAttribute("branchName", branchName);
			session.setAttribute("branchNum", branchNum);
			model.addAttribute("branchName", branchName);
			
			model.addAttribute("member", member);  
			List<BranchDTO> branchList = branchService.getBranchList();
			model.addAttribute("branchList", branchList);
			model.addAttribute("branchNum", branchNum);
			//log.debug(model.toString());
			return "branch/product";
		}
	}
	

//	장바구니에 제품 추가
	@ResponseBody
	@RequestMapping(value = {"/product/addcart"}, method = { RequestMethod.POST, RequestMethod.PATCH })
	public ResponseEntity<?> addCart(@RequestBody CartDTO cartDTO,  HttpSession session, Model model) {		
		try {
			boolean result= cartService.insertCart(cartDTO);
			return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(result));			
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}		
	}

	
//	장바구니 목록 불러오기
	@GetMapping(value="/product/cartlist/{userNum}/{branchNum}")
	@ResponseBody
	public Object cartList(
			@PathVariable("userNum") Long userNum
			, @PathVariable("branchNum") Long branchNum
			, HttpSession session, Model model) {		
			try {
				List<CartDTO> cartList = cartService.getCartList(userNum, branchNum);
				return cartList;
			} catch (Exception e) {			
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}		
		}

//	장바구니 제품 수량 변경
	@ResponseBody
	@RequestMapping(value={"/product/updatecart"}, method = { RequestMethod.POST, RequestMethod.PATCH })
	public ResponseEntity<?> updateCart2(@RequestBody CartDTO cartDTO , HttpSession session, Model model) {		
		try {
			boolean result= cartService.updateCartQty(cartDTO);
			return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(result));			
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}		
	}
	
	
//	장바구니 제품 삭제
	@ResponseBody
	@RequestMapping(value={"/product/deletecart"}, method= {RequestMethod.DELETE} )
	public ResponseEntity<?> deleteCart(@RequestBody CartDTO cartDTO, HttpSession session, Model model) {		
		try {
			boolean result= cartService.deleteCart(cartDTO);
			return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(result));			
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}		
	}
	
	
//	제품 카테고리에 따른 제품 목록 변경
	@ResponseBody
	@GetMapping(value = "/product/categoryProductList/{codeId}")
	public ResponseEntity<?> categoryProductList(@PathVariable("codeId") String codeId,
			ProductDTO  productDTO, Model model, HttpSession session) {		
		try {
			productDTO.setCodeId(codeId);	
			productDTO.setRecordsPerPage(12);
			List<ProductDTO> pList = productService.getProductList(productDTO);			
			return ResponseEntity.status(HttpStatus.OK).body(pList);			
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}		
	}

	
//	제품 상제 정보
	@ResponseBody
	@GetMapping(value = "/product/detail/{productNum}")
	public ResponseEntity<?> productDetail(@PathVariable Long productNum, Model model, HttpSession session) throws JsonProcessingException {

		ProductDTO product = productService.getProductDetail(productNum);
		if (product == null) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 제품이거나, 이미 삭제된 제품입니다");
		}
		
		
		Map<String,Object> map=new LinkedHashMap<>();
		List<ImgDTO> fileList =productService.getAttachFileList(productNum);
		map.put("product", product);
		map.put("fileList", fileList);
		String json=new ObjectMapper().writeValueAsString(map);

		return ResponseEntity.status(HttpStatus.OK).body(json);
	}
	
}
