package com.sparta.productservice.product.repository;

import static com.sparta.productservice.product.entity.QProductImage.productImage;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.productservice.product.dto.ProductImageInfoDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductImageQueryDSLRepositoryImpl implements ProductImageQueryDSLRepository{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ProductImageInfoDto> findProductImageList(Long productId) {
    return jpaQueryFactory.select(Projections.fields(ProductImageInfoDto.class,
                                                      productImage.id.as("productImageId"),
                                                      productImage.url))
        .from(productImage)
        .where(productImage.product.id.eq(productId))
        .fetch();
  }
}
