package com.sparta.productservice.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ItemStockDto {

  private Long optionItemId;
  private int stock;

  public static ItemStockDto of(Long optionItemId, int stock) {
    return ItemStockDto.builder()
        .optionItemId(optionItemId)
        .stock(stock)
        .build();
  }
}
