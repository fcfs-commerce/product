package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.product.dto.ProductInfoDto;
import com.sparta.productservice.product.dto.ProductOptionInfoDto;
import com.sparta.productservice.product.dto.ProductSummaryDto;
import com.sparta.productservice.product.type.Category;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductService {

  ApiResponse getProducts(String sortBy, int page, int size, boolean isAsc,
      Category category);

  ApiResponse getProduct(Long productId);

  ApiResponse getProductOptions(Long productId);
}
