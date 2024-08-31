package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.global.exception.CustomException;
import com.sparta.productservice.global.exception.ExceptionCode;
import com.sparta.productservice.global.util.ApiResponseUtil;
import com.sparta.productservice.product.dto.ProductImageInfoDto;
import com.sparta.productservice.product.dto.ProductInfoDto;
import com.sparta.productservice.product.dto.ProductOptionInfoDto;
import com.sparta.productservice.product.dto.ProductSummaryDto;
import com.sparta.productservice.product.entity.Product;
import com.sparta.productservice.product.repository.ProductImageRepository;
import com.sparta.productservice.product.repository.ProductOptionRepository;
import com.sparta.productservice.product.repository.ProductRepository;
import com.sparta.productservice.product.type.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final ProductOptionRepository productOptionRepository;

  @Override
  public ApiResponse getProducts(String sortBy, int page, int size, boolean isAsc,
      Category category) {
    Direction direction = isAsc ? Direction.ASC : Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<ProductSummaryDto> products = productRepository.findProducts(pageable, category);

    ApiResponse apiResponse = ApiResponseUtil.createSuccessResponse("Products loaded successfully.", products);
    return apiResponse;
  }

  @Override
  public ApiResponse getProduct(Long productId) {
    Product product = findProduct(productId);
    List<ProductImageInfoDto> productImageList = findProductImageList(productId);
    List<ProductOptionInfoDto> productOptionList = findProductOptionList(productId);
    ProductInfoDto productInfo = ProductInfoDto.of(product, productImageList, productOptionList);

    ApiResponse apiResponse = ApiResponseUtil.createSuccessResponse("Product loaded successfully.", productInfo);
    return apiResponse;
  }

  @Override
  public ApiResponse getProductOptions(Long productId) {
    List<ProductOptionInfoDto> productOptionList = findProductOptionList(productId);

    ApiResponse apiResponse = ApiResponseUtil.createSuccessResponse("Product option list loaded successfully.", productOptionList);
    return apiResponse;
  }

  private List<ProductOptionInfoDto> findProductOptionList(Long productId) {
    return productOptionRepository.findProductOptionList(productId);
  }

  private List<ProductImageInfoDto> findProductImageList(Long productId) {
    return productImageRepository.findProductImageList(productId);
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> CustomException.from(ExceptionCode.PRODUCT_NOT_FOUND));
  }
}