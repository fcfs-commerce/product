package com.sparta.productservice.product.controller;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.product.service.ProductService;
import com.sparta.productservice.product.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ApiResponse> getProducts(
      @RequestParam(value = "sortBy", required = false, defaultValue = "id")String sortBy,
      @RequestParam(value = "page")int page,
      @RequestParam(value = "size")int size,
      @RequestParam(value = "isAsc", required = false, defaultValue = "false")boolean isAsc,
      @RequestParam(value = "category", required = false) Category category) {
    ApiResponse apiResponse = productService.getProducts(sortBy, page-1, size, isAsc, category);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ApiResponse> getProduct(@PathVariable Long productId) {
    ApiResponse apiResponse = productService.getProduct(productId);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
  }

  @GetMapping("/{productId}/option")
  public ResponseEntity<ApiResponse> getProductOptions(@PathVariable Long productId) {
    ApiResponse apiResponse = productService.getProductOptions(productId);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
  }

  @GetMapping("/optionItem/{optionItemId}")
  public ResponseEntity<ApiResponse> getStock(@PathVariable Long optionItemId) {
    ApiResponse apiResponse = productService.getStock(optionItemId);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
  }
}
