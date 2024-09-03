package com.sparta.productservice.product.controller;

import com.sparta.productservice.product.dto.OptionItemDto;
import com.sparta.productservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/v1/products")
@RequiredArgsConstructor
public class ProductInternalController {

  private final ProductService productService;

  @GetMapping("/{productId}/option/{productOptionId}")
  public OptionItemDto findOptionItemIdByProductIdAndProductOptionId(@PathVariable Long productId,
      @PathVariable Long productOptionId) {
    return productService.findOptionItem(productId, productOptionId);
  }

  @GetMapping("{productId}")
  public OptionItemDto findOptionItemIdByProductId(@PathVariable Long productId) {
    return productService.findOptionItem(productId, null);
  }

}
