package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.miniproject.dto.CartDto;
import com.mycompany.miniproject.dto.OrderDetailDto;
import com.mycompany.miniproject.dto.OrderDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.service.OrderService;
import com.mycompany.miniproject.service.ProductService;
import com.mycompany.miniproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Secured("ROLE_user")
	@GetMapping("/addBasket")
	@ResponseBody
	public String addBasket(@RequestParam("productId")int productId, Authentication authentication) {
		CartDto cartDto = new CartDto();
		cartDto.setProductId(productId);
		String userId = authentication.getName();
		cartDto.setUserId(userId);
		if (orderService.checkCart(cartDto)) {
			cartDto.setProductQty(1);
			orderService.addCart(cartDto);
			return "successAdd";
		}else {
			return "exist";
		}
/*		return "redirect:/order/basket";*/
	}
	
	@Secured("ROLE_user")
	@GetMapping("/basket")
	public String basket(Authentication authentication, Model model) {
		String userId = authentication.getName();
		List<CartDto> cartList = orderService.getCart(userId);
		model.addAttribute("cartList", cartList);
		log.info(cartList.toString());
		return "order/basket";
	}
	
	@GetMapping("/loadMainImg")
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
	
	@PostMapping("/updateQty")
	@ResponseBody
	public void updateQty(@RequestParam("productId") int productId, @RequestParam("productQty") int productQty,
			Authentication authentication) {
		CartDto cartDto = new CartDto();
		cartDto.setProductId(productId);
		String userId = authentication.getName();
		cartDto.setUserId(userId);
		cartDto.setProductQty(productQty);
		orderService.updateQty(cartDto);
	}
	
	@Secured("ROLE_user")
	@GetMapping("/deleteBasket")
	public String deleteBasket(int productId, Authentication authentication) {
		CartDto cartDto = new CartDto();
		cartDto.setProductId(productId);
		String userId = authentication.getName();
		cartDto.setUserId(userId);
		orderService.deleteProduct(cartDto);
		return "redirect:/order/basket";
	}
	
	@PostMapping("/deleteBasketProducts")
	@ResponseBody
	public void deleteBasketProducts(@RequestBody ArrayList<Integer> productList, Authentication authentication) {
		for (int productId : productList) {
			CartDto cartDto = new CartDto();
			cartDto.setProductId(productId);
			String userId = authentication.getName();
			cartDto.setUserId(userId);
			orderService.deleteProduct(cartDto);
		}
	}
	
	@PostMapping("/orderSelectedProducts")
	@ResponseBody
	public void orderSelectedProducts(@RequestBody List<Integer> productList, HttpSession session) {
		session.setAttribute("productList", productList);
	}
	
	@PostMapping("/orderProducts")
	@ResponseBody
	public String orderProducts(
			@RequestBody OrderDetailDto orderDetail, 
			HttpSession session, 
			Authentication authentication) {
		OrderDto orderDto = new OrderDto();
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		
		// orders 테이블에 입력
		String userId = authentication.getName();
		int orderTotalPrice = orderDetail.getOrderTotalPrice();
		orderDto.setUserId(userId);
		orderDto.setOrderTotalPrice(orderTotalPrice);
		orderService.insertOrder(orderDto);
		
		// order_detail 테이블에 입력
		int orderId = orderDto.getOrderId();
		orderDetailDto.setOrderId(orderId);
		List<CartDto> orderProductList = (List<CartDto>) session.getAttribute("orderProductList");
		if (orderProductList != null) {
			for (CartDto cartDto : orderProductList) {
				orderDetailDto.setProductId(cartDto.getProductId());
				orderDetailDto.setProductQty(cartDto.getProductQty());
				orderDetailDto.setProductPrice(cartDto.getProductPrice());
				orderService.insertOrderDetail(orderDetailDto);
				log.info("장바구니 상품 결제 완료");
			}
		} else {
			orderDetailDto.setProductId(orderDetail.getProductId());
			orderDetailDto.setProductQty(1);
			orderDetailDto.setProductPrice(orderDetail.getProductPrice());
			orderService.insertOrderDetail(orderDetailDto);
			log.info("선택 상품 결제 완료");
		}
		
		int couponStatus = orderDetail.getCouponStatus();
		if (couponStatus != 0) {
			usedCoupon(userId);
		}
		
		return "" + orderId; // 주문번호를 String 타입으로 변환하여 리턴
	}
	
	public void usedCoupon(String userId) {
		// 쿠폰 사용시 상태 변경
		userService.updateCouponStatus(-1, userId);
	}
	
	@Secured("ROLE_user")
	@GetMapping("/order")
	public String order(String orderId, Model model) {
		model.addAttribute("orderId", orderId);
		return "order/order";
	}
	
	@Secured("ROLE_user")
	@GetMapping("/payment")
	public String payment(
			@RequestParam(required=false) Integer productId, HttpSession session, 
			Authentication authentication, Model model
		) {
		String userId = authentication.getName();
		if (productId == null) {
			@SuppressWarnings("unchecked")
			List<Integer> productList = (List<Integer>) session.getAttribute("productList");
			List<CartDto> selectedProductList = new ArrayList<>();
			for (int pid : productList) {
				CartDto cartDto = new CartDto();
				cartDto.setProductId(pid);
				cartDto.setUserId(userId);
				selectedProductList.add(orderService.getProduct(cartDto));
				session.setAttribute("orderProductList", selectedProductList);
			}
			model.addAttribute("selectedProductList", selectedProductList);
		} else {
			session.removeAttribute("orderProductList");
			session.removeAttribute("productList");
			ProductDto productInfo = productService.getProductDetail(productId);
			model.addAttribute("productInfo", productInfo);
		}
		
		int couponStatus = userService.getUserCouponStatus(userId);
		model.addAttribute("couponStatus", couponStatus);
		return "order/payment";
	}
	
	@GetMapping("/getCartNum")
	@ResponseBody
	public int getCartNum(Authentication authentication) {
		if (authentication == null || authentication.getName() == null) {
	        return 0;
	    }
	    return orderService.getCartNum(authentication.getName());
	}
}
