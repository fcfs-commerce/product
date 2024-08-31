package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>,
    ProductImageQueryDSLRepository {

}
