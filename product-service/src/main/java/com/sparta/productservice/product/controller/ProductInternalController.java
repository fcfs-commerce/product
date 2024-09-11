package com.sparta.productservice.product.controller;

import com.sparta.productservice.product.dto.OptionItemDto;
import com.sparta.productservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/v1/products")
@RequiredArgsConstructor
public class ProductInternalController {

  private final ProductService productService;

  @GetMapping("/optionItems/{optionItemId}")
  public OptionItemDto findOptionItemById(@PathVariable Long optionItemId) {
    return productService.findOptionItem(optionItemId);
  }

  @PutMapping("/optionItems/{optionItemId}")
  public void updateOptionItemStock(@PathVariable Long optionItemId, @RequestParam int stock) {
    productService.updateOptionItemStock(optionItemId, stock);
  }
}
