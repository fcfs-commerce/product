package com.sparta.productservice.product.dto;

import com.sparta.productservice.product.entity.OptionItem;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionItemDto {

  private Long optionItemId;
  private int stock;
  private int price;

  public static OptionItemDto from(Optional<OptionItem> optionItem) {
    return OptionItemDto.builder()
        .optionItemId(optionItem.get().getId())
        .stock(optionItem.get().getStock())
        .price(optionItem.get().getProduct().getPrice())
        .build();
  }
}
