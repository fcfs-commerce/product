package com.sparta.productservice.product.dto.response;

import com.sparta.productservice.product.entity.Product;
import com.sparta.productservice.product.entity.ProductImage;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductInfoDto {

  private Long productId;
  private String name;
  private int price;
  private String description;
  private List<ProductImageInfoDto> productImages;
  private List<ProductOptionInfoDto> productOptions;

  public static ProductInfoDto of(Product product, List<ProductImage> productImageList,
      List<ProductOptionInfoDto> productOptionList) {

    List<ProductImageInfoDto> productImageInfoDtoList = new ArrayList<>();
    for (ProductImage productImage : productImageList) {
      ProductImageInfoDto productImageInfoDto = ProductImageInfoDto.from(productImage);
      productImageInfoDtoList.add(productImageInfoDto);
    }

    return ProductInfoDto.builder()
        .productId(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .description(product.getDescription())
        .productImages(productImageInfoDtoList)
        .productOptions(productOptionList)
        .build();
  }
}
