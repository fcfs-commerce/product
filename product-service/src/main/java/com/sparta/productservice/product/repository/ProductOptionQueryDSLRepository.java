package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.dto.ProductOptionInfoDto;
import java.util.List;

public interface ProductOptionQueryDSLRepository {

  List<ProductOptionInfoDto> findProductOptionList(Long productId);

}
