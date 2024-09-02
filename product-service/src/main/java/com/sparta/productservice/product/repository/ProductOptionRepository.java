package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>, ProductOptionQueryDSLRepository {

}
