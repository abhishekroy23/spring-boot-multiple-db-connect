package com.demo.multidbdemo.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.multidbdemo.product.entity.Product;
import com.demo.multidbdemo.shop.entity.ShopDetail;

@Repository
public interface ShopDetailRepo extends JpaRepository<ShopDetail, Long>{

}
