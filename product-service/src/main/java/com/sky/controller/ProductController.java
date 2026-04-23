package com.sky.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.model.Product;
import com.sky.repo.ProductRepo;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/all")
	public List<Product> fetchProducts(){
		return productRepo.findAll();
	}
	
	@QueryMapping
	public List<Product> getProducts(){
		return productRepo.findAll();
	}
	
	@QueryMapping
	public List<Product> getProductsBYCategory(@Argument String category){
		return productRepo.findByCategory(category);
	}
	
	@MutationMapping
	public Product updateStockById(@Argument int id, @Argument int stock) {
		Product product = productRepo.findById(id).orElseThrow(()-> new RuntimeException("product not found with this id"));
		product.setStock(stock);
		return productRepo.save(product);
	}

}
