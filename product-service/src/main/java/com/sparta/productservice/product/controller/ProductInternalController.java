package com.sparta.productservice.product.controller;

import com.sparta.productservice.product.dto.request.UpdateItemStockDto;
import com.sparta.productservice.product.dto.response.OptionItemDto;
import com.sparta.productservice.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @PutMapping("/optionItems/stock-decrease")
  public List<OptionItemDto> decreaseStock(@RequestBody List<UpdateItemStockDto> requestDto) {
    return productService.decreaseStock(requestDto);
  }

  @PutMapping("/optionItems/stock-increase")
  public void increaseStock(@RequestBody List<UpdateItemStockDto> optionItemsIdList) {
    productService.increaseStock(optionItemsIdList);
  }
}
