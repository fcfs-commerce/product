package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.product.dto.OptionItemDto;
import com.sparta.productservice.product.type.Category;

public interface ProductService {

  ApiResponse getProducts(String sortBy, int page, int size, boolean isAsc,
      Category category);

  ApiResponse getProduct(Long productId);

  ApiResponse getProductOptions(Long productId);

  OptionItemDto findOptionItem(Long productId, Long productOptionId);
}
