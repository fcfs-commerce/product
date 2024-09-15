package com.sparta.productservice.product.dto.response;

import com.sparta.productservice.product.entity.OptionItem;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionItemDto {

  private Long optionItemId;
  private int quantity;
  private int price;

  public static OptionItemDto of(OptionItem optionItem, int quantity) {
    return OptionItemDto.builder()
        .optionItemId(optionItem.getId())
        .quantity(quantity)
        .price(optionItem.getProduct().getPrice())
        .build();
  }

  public static OptionItemDto from(OptionItem optionItem) {
    return OptionItemDto.builder()
        .optionItemId(optionItem.getId())
        .build();
  }
}
