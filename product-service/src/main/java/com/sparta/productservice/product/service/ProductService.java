package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.product.dto.response.OptionItemDto;
import com.sparta.productservice.product.dto.request.HoldItemStockRequestDto;
import com.sparta.productservice.product.type.Category;

public interface ProductService {

  ApiResponse getProducts(String sortBy, int page, int size, boolean isAsc,
      Category category);

  ApiResponse getProduct(Long productId);

  ApiResponse getProductOptions(Long productId);

  OptionItemDto findOptionItem(Long optionItemId);

  void updateOptionItemStock(Long optionItemId, int stock);

  ApiResponse getStock(Long optionItemId);

  ApiResponse holdStock(HoldItemStockRequestDto requestDto);
}
