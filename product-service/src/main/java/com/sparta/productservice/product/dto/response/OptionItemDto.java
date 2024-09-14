package com.sparta.productservice.product.dto.response;

import com.sparta.productservice.product.entity.OptionItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionItemDto {

  private Long optionItemId;
  private int stock;
  private int price;

  public static OptionItemDto from(OptionItem optionItem) {
    return OptionItemDto.builder()
        .optionItemId(optionItem.getId())
        .stock(optionItem.getStock())
        .price(optionItem.getProduct().getPrice())
        .build();
  }
}
