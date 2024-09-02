package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.dto.ProductSummaryDto;
import com.sparta.productservice.product.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryDSLRepository {

  Page<ProductSummaryDto> findProducts(Pageable pageable,
      Category category);

}
