package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.dto.ProductImageInfoDto;
import java.util.List;

public interface ProductImageQueryDSLRepository {

  List<ProductImageInfoDto> findProductImageList(Long productId);

}
