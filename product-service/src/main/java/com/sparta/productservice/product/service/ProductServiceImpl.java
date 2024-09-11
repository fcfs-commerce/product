package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.global.exception.CustomException;
import com.sparta.productservice.global.exception.ExceptionCode;
import com.sparta.productservice.global.util.ApiResponseUtil;
import com.sparta.productservice.product.dto.ItemStockDto;
import com.sparta.productservice.product.dto.OptionItemDto;
import com.sparta.productservice.product.dto.ProductInfoDto;
import com.sparta.productservice.product.dto.ProductOptionInfoDto;
import com.sparta.productservice.product.dto.ProductSummaryDto;
import com.sparta.productservice.product.entity.OptionItem;
import com.sparta.productservice.product.entity.Product;
import com.sparta.productservice.product.entity.ProductImage;
import com.sparta.productservice.product.repository.OptionItemRepository;
import com.sparta.productservice.product.repository.ProductImageRepository;
import com.sparta.productservice.product.repository.ProductOptionRepository;
import com.sparta.productservice.product.repository.ProductRepository;
import com.sparta.productservice.product.repository.StockRepository;
import com.sparta.productservice.product.type.Category;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final ProductOptionRepository productOptionRepository;
  private final OptionItemRepository optionItemRepository;
  private final StockRepository stockRepository;

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
    List<ProductImage> productImageList = findProductImageList(product);
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

  @Override
  public OptionItemDto findOptionItem(Long productId, Long productOptionId) {
    Optional<OptionItem> optionItem = optionItemRepository.findByProductIdAndProductOptionId(productId, productOptionId);

    if (optionItem.isEmpty()) {
      return null;
    } else {
      return OptionItemDto.from(optionItem.get());
    }
  }

  @Override
  @Transactional
  public void updateOptionItemStock(Long optionItemId, int stock) {
    OptionItem optionItem = optionItemRepository.findById(optionItemId)
        .orElseThrow(() -> CustomException.from(ExceptionCode.OPTION_ITEM_NOT_FOUND));

    optionItem.updateStock(stock);
  }

  @Override
  public ApiResponse getStock(Long optionItemId) {
    String key = "OPTION_ITEM_ID:" + optionItemId;

    String stockValue = stockRepository.getValueByKey(key);
    int stock;
    if (stockValue == null || Integer.parseInt(stockValue) == 0) {
      OptionItem optionItem = optionItemRepository.findById(optionItemId)
          .orElseThrow(() -> CustomException.from(ExceptionCode.OPTION_ITEM_NOT_FOUND));

      stock = optionItem.getStock();

      stockRepository.setValue(key, String.valueOf(stock), Duration.ofMinutes(1));
    } else {
      stock = Integer.parseInt(stockValue);
    }

    ItemStockDto itemStockDto = ItemStockDto.of(optionItemId, stock);
    return ApiResponseUtil.createSuccessResponse("The stock of the OptionItem "+optionItemId + " loaded successfully.", itemStockDto);
  }

  private List<ProductOptionInfoDto> findProductOptionList(Long productId) {
    return productOptionRepository.findProductOptionList(productId);
  }

  private List<ProductImage> findProductImageList(Product product) {
    return productImageRepository.findAllByProduct(product);
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> CustomException.from(ExceptionCode.PRODUCT_NOT_FOUND));
  }
}
