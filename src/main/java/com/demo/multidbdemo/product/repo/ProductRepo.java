package com.demo.multidbdemo.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.multidbdemo.product.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

}
