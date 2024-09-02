package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.entity.Product;
import com.sparta.productservice.product.entity.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

  List<ProductImage> findAllByProduct(Product product);

}
