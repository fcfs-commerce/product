package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryDSLRepository {

}
