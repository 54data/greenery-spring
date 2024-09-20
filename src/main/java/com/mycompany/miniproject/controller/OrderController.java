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
	public String addBasket(int productId, Authentication authentication) {
		CartDto cartDto = new CartDto();
		cartDto.setProductId(productId);
		String userId = authentication.getName();
		cartDto.setUserId(userId);
		if (orderService.checkCart(cartDto)) {
			cartDto.setProductQty(1);
			orderService.addCart(cartDto);
		}
		return "redirect:/order/basket";
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
	
	@RequestMapping("/order")
	public String order() {
		log.info("실행");
		return "order/order";
	}
	
	@Secured("ROLE_user")
	@GetMapping("/payment")
	public String payment(
			@RequestParam(required=false) Integer productId, HttpSession session, 
			Authentication authentication, Model model
		) {
		if (productId == null) {
			@SuppressWarnings("unchecked")
			List<Integer> productList = (List<Integer>) session.getAttribute("productList");
			List<CartDto> selectedProductList = new ArrayList<>();
			String userId = authentication.getName();
			for (int pid : productList) {
				CartDto cartDto = new CartDto();
				cartDto.setProductId(pid);
				cartDto.setUserId(userId);
				selectedProductList.add(orderService.getProduct(cartDto));
			}
			model.addAttribute("selectedProductList", selectedProductList);
		} else {
			session.removeAttribute("productList");
			ProductDto productInfo = productService.getProductDetail(productId);
			model.addAttribute("productInfo", productInfo);
		}
		
		int couponStatus = userService.getUserCouponStatus(authentication.getName());
		model.addAttribute("couponStatus", couponStatus);
		return "order/payment";
	}
}
