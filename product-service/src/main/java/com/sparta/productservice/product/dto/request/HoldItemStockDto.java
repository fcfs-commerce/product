package com.sparta.productservice.product.dto.request;

import lombok.Getter;

@Getter
public class HoldItemStockDto {

  private Long optionItemId;
  private int quantity;

}
