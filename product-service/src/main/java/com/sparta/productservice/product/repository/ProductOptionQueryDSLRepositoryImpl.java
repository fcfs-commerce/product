package com.sparta.productservice.product.repository;

import static com.sparta.productservice.product.entity.QOptionItem.optionItem;
import static com.sparta.productservice.product.entity.QProductOption.productOption;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.productservice.product.dto.response.ProductOptionInfoDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductOptionQueryDSLRepositoryImpl implements ProductOptionQueryDSLRepository{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ProductOptionInfoDto> findProductOptionList(Long productId) {
    return jpaQueryFactory.select(Projections.fields(ProductOptionInfoDto.class,
                                                      optionItem.id.as("optionItemId"),
                                                      productOption.id.as("productOptionId"),
                                                      productOption.name))
        .from(optionItem)
        .leftJoin(optionItem.productOption)
        .where(optionItem.product.id.eq(productId))
        .fetch();
  }
}
