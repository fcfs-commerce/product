package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.product.dto.request.UpdateItemStockDto;
import com.sparta.productservice.product.dto.response.OptionItemDto;
import com.sparta.productservice.product.dto.request.HoldItemStockRequestDto;
import com.sparta.productservice.product.type.Category;
import java.util.List;

public interface ProductService {

  ApiResponse getProducts(String sortBy, int page, int size, boolean isAsc,
      Category category);

  ApiResponse getProduct(Long productId);

  ApiResponse getProductOptions(Long productId);

  OptionItemDto findOptionItem(Long optionItemId);

  ApiResponse getStock(Long optionItemId);

  ApiResponse holdStock(HoldItemStockRequestDto requestDto);

  List<OptionItemDto> decreaseStock(List<UpdateItemStockDto> requestDto);

  void increaseStock(List<UpdateItemStockDto> optionItemsIdList);
}
