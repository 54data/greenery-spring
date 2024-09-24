package com.mycompany.miniproject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.dto.ReviewDataDto;
import com.mycompany.miniproject.dto.ReviewDto;
import com.mycompany.miniproject.dto.UserDto;
import com.mycompany.miniproject.security.UsersDetails;
import com.mycompany.miniproject.security.UsersDetailsService;
import com.mycompany.miniproject.service.OrderDetailService;
import com.mycompany.miniproject.service.ProductService;
import com.mycompany.miniproject.service.ReviewService;
import com.mycompany.miniproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@Slf4j
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
	private UsersDetailsService usersDetailsService;
	
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
	    UsersDetails userDetails = (UsersDetails) usersDetailsService.loadUserByUsername(userId);
	    
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
	
	@GetMapping("/likedProducts")
	public String likedProducts(Model model, Authentication authentication) {
		log.info("실행");
		List<Integer> wishlist = productService.getWishlistAll(authentication.getName());
		List<ProductAddDto> productList = new ArrayList<>();
		for(int i : wishlist) {
			productList.add(productService.getProductByProductId(i));
		}
		log.info(productList.toString());
		model.addAttribute("productList", productList);
		return "mypage/likedProducts";
	}
	
	@RequestMapping("/mypage")
	public String mypage() {
		log.info("실행");
		return "mypage/mypage";
	}
	
	@GetMapping("/orderList")
	public String orderList(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userId =  authentication.getName();

	    log.info("User ID: " + userId);

	    List<OrderDetailDto> orderDetails = orderDetailService.getOrderDetailsByOd(userId);
	    String userName = userService.getUserName(userId);
	    
	    for (OrderDetailDto orderDetail : orderDetails) {
	    	log.info("Order ID: " + orderDetail.getOrderId());
	    	log.info("Product ID: " + orderDetail.getProductId());    	
	    	
	    	ReviewDto review = reviewService.getReview(orderDetail.getOrderId(), orderDetail.getProductId());
    	    orderDetail.setReview(review);
	        boolean hasReview = reviewService.hasReviewForProduct(orderDetail.getOrderId(), orderDetail.getProductId());
	        orderDetail.setHasReview(hasReview);
	    }
	    
	    model.addAttribute("orderDetails", orderDetails);
	    model.addAttribute("userName", userName);
	    model.addAttribute("userId", userId);
	    
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
		
	@GetMapping("/deleteReview")
	public String deleteReview(int reviewId) {
		reviewService.deleteReview(reviewId);
		return "redirect:/mypage/mypage";
	}
	
	@PostMapping("/reviewInsert")
	public String reviewInsert(@ModelAttribute ReviewDataDto reviewDataDto, @RequestParam(value = "reviewImg", required = false) MultipartFile reviewImage){
		log.info("Review DTO: " + reviewDataDto);
		log.info("실행");
		
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
