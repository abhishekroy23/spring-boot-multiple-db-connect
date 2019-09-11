package com.demo.multidbdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.multidbdemo.product.entity.Product;
import com.demo.multidbdemo.product.repo.ProductRepo;
import com.demo.multidbdemo.shop.entity.ShopDetail;
import com.demo.multidbdemo.shop.repo.ShopDetailRepo;

@RestController
public class RestControllerDemo {

	@Autowired
	ProductRepo productRepo;
	@Autowired
	ShopDetailRepo shopDetailRepo;
	
	@PostMapping("/product")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){		
		product = productRepo.save(product);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PostMapping("/shop")
	public ResponseEntity<ShopDetail> checkLoanEligibility(@RequestBody ShopDetail shopDetail){		
		shopDetail = shopDetailRepo.save(shopDetail);
		return new ResponseEntity<>(shopDetail, HttpStatus.OK);
	}
}
