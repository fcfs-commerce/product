package com.sparta.productservice.product.dto.response;

import com.sparta.productservice.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductImageInfoDto {

  private Long productImageId;
  private String url;

  public static ProductImageInfoDto from(ProductImage productImage) {
    return ProductImageInfoDto.builder()
        .productImageId(productImage.getId())
        .url(productImage.getUrl())
        .build();
  }
}
