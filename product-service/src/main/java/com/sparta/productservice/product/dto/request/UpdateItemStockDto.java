package com.sparta.productservice.product.dto.request;

import lombok.Getter;

@Getter
public class UpdateItemStockDto {

  private Long optionItemId;
  private int quantity;

}
