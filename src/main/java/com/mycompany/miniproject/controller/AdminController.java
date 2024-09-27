package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.miniproject.dto.NoticeDto;
import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.service.NoticeService;
import com.mycompany.miniproject.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
@Secured("ROLE_admin")
public class AdminController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/mainadmin")
	public String mainAdmin() {
		log.info("실행");
		return "redirect:/admin/productselect";
	}
	
	@GetMapping("noticeUpdateForm")
	public String noticeAddForm(String pageUsage, int noticeId, Model model) {
		if (pageUsage.equals("수정")) {
			NoticeDto notice = noticeService.getNoticeContent(noticeId);
			model.addAttribute("notice", notice);
		}
		return "admin/noticeadd";
	}
	
	@GetMapping("/noticeadd")
	public String noticeAdd(String pageUsage) {
		return "admin/noticeadd";
	}
	
	@GetMapping("/noticeselect")
	public String noticeSelect(
			Model model, 
			@RequestParam(defaultValue="1") int pageNo,
			HttpSession session) {
		int totalRows = noticeService.getTotalRows();
		PagerDto pager = new PagerDto(7, 5, totalRows, pageNo);
		session.setAttribute("pager", pager);
		List<NoticeDto> noticeList = noticeService.getNotices(pager);
		model.addAttribute("noticeList", noticeList);
		return "admin/noticeselect";
	}
	
	@GetMapping("/productadd")
	public String productAdd(String pageUsage) {
		log.info("실행");
		return "admin/productadd";
	}
	
	@GetMapping("/productselect")
	public String productselect(Model model,
				@RequestParam(defaultValue="1")int pageNo,
				HttpSession session) {
		
		int totalRows = productService.getTotalRows();
		PagerDto pager = new PagerDto(10, 5, totalRows, pageNo);
		pager.setSort("regDateDesc");
		model.addAttribute("pager", pager);
		
		List<ProductDto> productList = productService.getProducts(pager);
				
		model.addAttribute("productList", productList);
		log.info("실행");
		 log.info("Product List: " + productList);
		
		return "admin/productselect";
	}
	
	@GetMapping("loadMainImg")
	public void loadMainImg(int productId, HttpServletResponse response) throws Exception {
		ProductImageDto productImage = productService.getMainImg(productId);
		
		String contentType = productImage.getProductImgType();
		response.setContentType(contentType);
		
		String fileName = productImage.getProductImgName();
		String encodingFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\"");		

		OutputStream out = response.getOutputStream();
		out.write(productImage.getProductImg());
		out.flush();
		out.close();
	}

	@GetMapping("loadImgByUsage")
	public void loadImgByUsage(int productId, String usage, HttpServletResponse response) throws Exception {		
		ProductImageDto productImageDto = new ProductImageDto();
		ProductImageDto productImage = productService.getImgByUsage(productId, usage);
		
		String contentType = productImage.getProductImgType();
		response.setContentType(contentType);
		
		String fileName = productImage.getProductImgName();
		String encodingFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\"");		
		
		OutputStream out = response.getOutputStream();
		out.write(productImage.getProductImg());
		out.flush();
		out.close();
	}
	
	@PostMapping("/productInsert")
	public String productInsert(ProductAddDto prdAddDto) throws Exception{
		log.info("실행");
		productService.insertProduct(prdAddDto);
		int productId = productService.getProductIdByName(prdAddDto.getProductName());
		
		if(!prdAddDto.getProductMainImage().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "main", prdAddDto.getProductMainImage());
		}
		if(!prdAddDto.getProductSub1Image().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "sub1", prdAddDto.getProductSub1Image());
		}
		if(!prdAddDto.getProductSub2Image().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "sub2", prdAddDto.getProductSub2Image());
		}
		if(!prdAddDto.getProductSub3Image().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "sub3", prdAddDto.getProductSub3Image());
		}
		if(!prdAddDto.getProductDetailImage().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "detail", prdAddDto.getProductDetailImage());
		}
		return "redirect:/admin/productselect";
	}
	
	private void insertProduct(String category, int productId, String usage, MultipartFile mf) throws Exception {
		ProductImageDto imgDto = new ProductImageDto();
		imgDto.setProductId(productId);
		imgDto.setProductImgUsage(usage);
		imgDto.setProductImgName(category + "_" + productId + "_" + usage);
		imgDto.setProductImg(mf.getBytes());
		imgDto.setProductImgType(mf.getContentType());
		productService.insertProductImg(imgDto);
	}
	
	@GetMapping("/updateForm")
	public String updateForm(int productId, String pageUsage, Model model) {
		ProductAddDto product = productService.getProductByProductId(productId);
		model.addAttribute("product", product);
		
		model.addAttribute("mainImage", productService.getImgByUsage(productId, "main") != null);
	    model.addAttribute("sub1Image", productService.getImgByUsage(productId, "sub1") != null);
	    model.addAttribute("sub2Image", productService.getImgByUsage(productId, "sub2") != null);
	    model.addAttribute("sub3Image", productService.getImgByUsage(productId, "sub3") != null);
	    model.addAttribute("detailImage", productService.getImgByUsage(productId, "detail") != null);
	    
		return "admin/productadd";
	}	

	@PostMapping("/updateProduct")
	public String updateProduct(ProductAddDto prdAddDto) throws Exception{
		log.info("실행");
		productService.updateProduct(prdAddDto);
		
		if(!prdAddDto.getProductMainImage().isEmpty()) {
			int result = updateProductImage(prdAddDto.getProductId(), "main", prdAddDto.getProductMainImage());
			if(result == 0) {
				insertProduct(prdAddDto.getCategory(), prdAddDto.getProductId(), "main", prdAddDto.getProductMainImage());				
			}
		}
		
		if(!prdAddDto.getProductSub1Image().isEmpty()) {
			int result = updateProductImage(prdAddDto.getProductId(), "sub1", prdAddDto.getProductSub1Image());
			if(result == 0) {
				insertProduct(prdAddDto.getCategory(), prdAddDto.getProductId(), "sub1", prdAddDto.getProductSub1Image());			
			}
		}
		
		if(!prdAddDto.getProductSub2Image().isEmpty()) {
			int result = updateProductImage(prdAddDto.getProductId(), "sub2", prdAddDto.getProductSub2Image());
			if(result == 0) {
				insertProduct(prdAddDto.getCategory(), prdAddDto.getProductId(), "sub2", prdAddDto.getProductSub2Image());			
			}
		}
		
		if(!prdAddDto.getProductSub3Image().isEmpty()) {
			int result = updateProductImage(prdAddDto.getProductId(), "sub3", prdAddDto.getProductSub3Image());
			if(result == 0) {
				insertProduct(prdAddDto.getCategory(), prdAddDto.getProductId(), "sub3", prdAddDto.getProductSub3Image());			
			}
		}
		
		if(!prdAddDto.getProductDetailImage().isEmpty()) {
			int result = updateProductImage(prdAddDto.getProductId(), "detail", prdAddDto.getProductDetailImage());
			if(result == 0) {
				insertProduct(prdAddDto.getCategory(), prdAddDto.getProductId(), "detail", prdAddDto.getProductDetailImage());			
			}
		}
		
		return "redirect:/admin/mainadmin";
	}
	
	private int updateProductImage( int productId, String usage, MultipartFile mf) throws Exception {
		ProductImageDto imgDto = new ProductImageDto();
		imgDto.setProductId(productId);
		imgDto.setProductImgUsage(usage);
		imgDto.setProductImg(mf.getBytes());
		imgDto.setProductImgType(mf.getContentType());
		int result = productService.updateProductImage(imgDto);
		return result;
	}
	
	@GetMapping("deleteProductImg")
	public String deleteProductImg(int productId, String usage) throws Exception {
		productService.deleteProductImg(productId, usage);
		String encodedPageUsage = URLEncoder.encode("수정", "UTF-8");
		return "redirect:/admin/updateForm?productId=" + productId + "&pageUsage="+ encodedPageUsage;
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(int productId) {
		productService.deleteProduct(productId);
		return "redirect:/admin/productselect";
	}
	
	@PostMapping("/addNotice") 
	public String addNotice(NoticeDto noticeForm) {
		NoticeDto notice = new NoticeDto();
		notice.setNoticeWriter("greenery_admin"); // 임시 
		notice.setNoticeTitle(noticeForm.getNoticeTitle());
		notice.setNoticeContent(noticeForm.getNoticeContent());
		notice.setNoticeHitcount(0);
		noticeService.addNoticeContent(notice);
		return "redirect:/admin/noticeselect";
	}
	
	@PostMapping("/updateNotice")
	public String updateNotice(NoticeDto noticeForm) {
		NoticeDto notice = new NoticeDto();
		notice.setNoticeId(noticeForm.getNoticeId());
		notice.setNoticeTitle(noticeForm.getNoticeTitle());
		notice.setNoticeContent(noticeForm.getNoticeContent());
		noticeService.updateNotice(notice);
		return "redirect:/admin/noticeselect";
	}
	
	@GetMapping("/deleteNotice") 
	public String deleteNotice(int noticeId) {
		noticeService.deleteNotice(noticeId);
		return "redirect:/admin/noticeselect";
	}
	
}
