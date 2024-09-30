package com.mycompany.miniproject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.miniproject.dto.OrderDetailDto;
import com.mycompany.miniproject.dto.OrderDto;
import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.dto.ReviewDataDto;
import com.mycompany.miniproject.dto.ReviewDto;
import com.mycompany.miniproject.dto.UserDto;
import com.mycompany.miniproject.security.CustomUserDetailsService;
import com.mycompany.miniproject.service.OrderDetailService;
import com.mycompany.miniproject.service.OrderService;
import com.mycompany.miniproject.service.ProductService;
import com.mycompany.miniproject.service.ReviewService;
import com.mycompany.miniproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@Slf4j
@Secured("ROLE_user")
public class MypageController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService usersDetailsService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/editMyInfo")
	public String editMyInfo(Authentication authentication, Model model) {
		String userId = authentication.getName();
		UserDto user = userService.getUserInfo(userId);
		model.addAttribute("user", user);
		return "mypage/editMyInfo";
	}
	
	@PostMapping("/updateMyInfo")
	@ResponseBody
	public String updateMyInfo(@RequestBody UserDto userDto, Authentication authentication) {
		String userId = authentication.getName();
		UserDto user = new UserDto();
		user.setUserId(userId);
		user.setUserTel(userDto.getUserTel());
		user.setUserEmail(userDto.getUserEmail());
		user.setZipcode(userDto.getZipcode());
		user.setRoadAddress(userDto.getRoadAddress());
		user.setDetailedAddress(userDto.getDetailedAddress());
		userService.updateUserInfo(user);
		return "editMyInfo";
	}
	
	@PostMapping("/updateUserPassword")
	@ResponseBody
	public String updateUserPassword(@RequestBody Map<String, String> pwdData, Authentication authentication) {
	    String currentPwd = pwdData.get("currentPwd");
	    String newPwd = pwdData.get("newPwd");
	    
	    String userId = authentication.getName();
	    UserDto user = userService.getUserInfo(userId);
	    UserDetails userDetails = usersDetailsService.loadUserByUsername(userId);
	    
	    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    if (!passwordEncoder.matches(currentPwd, user.getUserPwd())) {
	        return "NOT EQUAL";
	    }
	    
	    String encodedNewPwd = passwordEncoder.encode(newPwd);
	    user.setUserPwd(encodedNewPwd);
	    
	    Boolean updateResult = userService.updatePwd(user);
	    if (updateResult) {
	    	Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(newAuth);
	        return "SUCCESS";
	    }
	    return "FAIL";
	}
	
	@PostMapping("/deactivateUser")
	@ResponseBody
	public String deactivateUser(Authentication authentication) {
		userService.deactivateUser(authentication.getName());
		return "deactivate";
	}
	
	@GetMapping("/likedProducts")
	public String likedProducts(Model model, Authentication authentication, @RequestParam(defaultValue="1")int pageNo) {
		log.info("실행");
		int totalRows = productService.getTotalWishlistRows(authentication.getName());
		PagerDto pager = new PagerDto(10, 5, totalRows, pageNo);
		pager.setUserId(authentication.getName());
		List<ProductAddDto> wishlistProduct = productService.getWishlistAll(pager);
		model.addAttribute("productList", wishlistProduct);
		model.addAttribute("pager", pager);
		
		if(authentication != null) {
			List<Integer> userWishlist = productService.getUserWishlist(authentication.getName());
			Map<Integer, Boolean> isWishlist = new HashMap<>();
			for(ProductAddDto product :wishlistProduct) {
				isWishlist.put(product.getProductId(), userWishlist.contains(product.getProductId()));
			}
			model.addAttribute("isWishlist", isWishlist);
		}
		
		UserDto userInfo = userService.getUserInfo(authentication.getName());
		model.addAttribute("userInfo", userInfo);
		
		return "mypage/likedProducts";
	}
	
	@RequestMapping("/mypage")
	public String mypage() {
		log.info("실행");
		return "mypage/mypage";
	}

	@RequestMapping("/orderList")
	public String orderList(Model model, @RequestParam(defaultValue = "1") int pageNo, 
			@RequestParam(defaultValue = "0")String sortOrder, @RequestParam(defaultValue = "")String searchQuery, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId =  authentication.getName();
	

	    List<OrderDetailDto> orderDetails = orderDetailService.getOrderDetails(userId, sortOrder, searchQuery);	    
	    List<Integer> orderIds = new ArrayList<>();
	    for (OrderDetailDto orderDetail : orderDetails) {
	    	ReviewDto review = reviewService.getReview(orderDetail.getOrderId(), orderDetail.getProductId());
		    orderDetail.setReview(review);
	        boolean hasReview = reviewService.hasReviewForProduct(orderDetail.getOrderId(), orderDetail.getProductId());
	        orderDetail.setHasReview(hasReview);
	        
	        if (!orderIds.contains(orderDetail.getOrderId())) {
	            orderIds.add(orderDetail.getOrderId());
	        }
	    }

	    PagerDto pager = new PagerDto(5, 5, orderIds.size(), pageNo);
	    pager.setSort(sortOrder);
	    pager.setSearchQuery(searchQuery);
	    
	    log.info("나는 sort야.." + sortOrder);
	    log.info("나는 검색내용이얌.." + searchQuery);
	    
	    
	    if (!orderIds.isEmpty()) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("orderIds", orderIds);
	        params.put("pager", pager);
	        List<OrderDto> filteredOrders = orderService.getOrdersByOrderIds(params);
	        model.addAttribute("orderDtos", filteredOrders);
	        model.addAttribute("orderDetails", orderDetails);
	    }

	    session.setAttribute("pager", pager);
	    UserDto userInfo = userService.getUserInfo(userId);
		model.addAttribute("userInfo", userInfo);
	    
	    
	    log.info("실행");
	    return "mypage/orderList";
	}
	
	@GetMapping("/getReviewInfo")
	@ResponseBody
	public ReviewDto getReviewInfo(@RequestParam int reviewId, @RequestParam int productId, @RequestParam int orderId) {
	    ReviewDto review = reviewService.getReviewByReviewId(reviewId);
	    OrderDetailDto orderDetail = orderDetailService.getOrderDetailById(orderId, productId);
	    
	    review.setOrderDetail(orderDetail);
	    return review;
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
	
	@GetMapping("/reviews")
	public String reviews() {
		log.info("실행");
		return "mypage/reviews";
	}
		
	@PostMapping("/deleteReview")
	public ResponseEntity<String> deleteReview(@RequestParam int reviewId) {
		log.info("실행" + reviewId);
		try {
	        reviewService.deleteReview(reviewId);
	        return ResponseEntity.ok("OK");  
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");  
	    }
	}
	
	@PostMapping("/reviewInsert")
	public String reviewInsert(@ModelAttribute ReviewDataDto reviewDataDto, @RequestParam(value = "reviewImg", required = false) MultipartFile reviewImage){
		log.info("Review DTO: " + reviewDataDto);
		log.info("실행");
		
		if (reviewDataDto.getReviewScore() == 0) {
	        //에러발생
			return "redirect:/mypage/orderList";
	    }
	    if (reviewDataDto.getReviewContent() == null || reviewDataDto.getReviewContent().isEmpty()) {
	        //에러발생
	    	return "redirect:/mypage/orderList";
	    }
		
		ReviewDto review = new ReviewDto();
		
		review.setOrderId(reviewDataDto.getOrderId());
		review.setProductId(reviewDataDto.getProductId());
		review.setUserId(reviewDataDto.getUserId());
		review.setReviewContent(reviewDataDto.getReviewContent());
		review.setReviewScore(reviewDataDto.getReviewScore());
		
		MultipartFile getReviewImage = reviewDataDto.getReviewImg();
		
		try {
	        if (reviewImage != null) {
	        	review.setReviewImg(getReviewImage.getBytes());
	        	review.setReviewImgType(getReviewImage.getContentType());
	        	review.setReviewImgName(getReviewImage.getOriginalFilename());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		reviewService.insertReview(review);	
		
		return "redirect:/mypage/orderList";
	}
	
	@ResponseBody
	@PostMapping("/updateReview")
	public String updateReview(@ModelAttribute ReviewDataDto reviewDataDto, @RequestParam(value = "reviewImg", required = false) MultipartFile reviewImage) {
		log.info("Review DTO: " + reviewDataDto);
		log.info("실행");
		
		if (reviewDataDto.getReviewScore() == 0) {
	        //에러발생
			return "redirect:/mypage/orderList";
	    }
	    if (reviewDataDto.getReviewContent() == null || reviewDataDto.getReviewContent().isEmpty()) {
	        //에러발생
	    	return "redirect:/mypage/orderList";
	    }

		ReviewDto review = new ReviewDto();
		
		review.setReviewId(reviewDataDto.getReviewId());	
		review.setOrderId(reviewDataDto.getOrderId());
		review.setProductId(reviewDataDto.getProductId());
		review.setUserId(reviewDataDto.getUserId());
		review.setReviewContent(reviewDataDto.getReviewContent());
		review.setReviewScore(reviewDataDto.getReviewScore());
		
		MultipartFile getReviewImage = reviewDataDto.getReviewImg();
		
		try {
	        if (reviewImage != null) {
	        	review.setReviewImg(getReviewImage.getBytes());
	        	review.setReviewImgType(getReviewImage.getContentType());
	        	review.setReviewImgName(getReviewImage.getOriginalFilename());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		reviewService.updateReview(review);	
		
		return "redirect:/mypage/orderList";
	}
	
	@GetMapping("loadReviewImg")
	public void loadReviewImg(@RequestParam int reviewId, HttpServletResponse response) throws Exception {
		ReviewDto reviewImg = reviewService.getReviewImgByReviewId(reviewId);
		
		String contentType = reviewImg.getReviewImgType();
		response.setContentType(contentType);
		
		String fileName = reviewImg.getReviewImgName();
		String encodingFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\"");		

		OutputStream out = response.getOutputStream();
		out.write(reviewImg.getReviewImg());
		out.flush();
		out.close();
	}
}