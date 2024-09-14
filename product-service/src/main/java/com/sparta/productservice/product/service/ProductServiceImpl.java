package com.sparta.productservice.product.service;

import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.global.exception.CustomException;
import com.sparta.productservice.global.exception.ExceptionCode;
import com.sparta.productservice.global.util.ApiResponseUtil;
import com.sparta.productservice.product.dto.response.ItemStockDto;
import com.sparta.productservice.product.dto.response.OptionItemDto;
import com.sparta.productservice.product.dto.response.ProductInfoDto;
import com.sparta.productservice.product.dto.response.ProductOptionInfoDto;
import com.sparta.productservice.product.dto.response.ProductSummaryDto;
import com.sparta.productservice.product.dto.request.HoldItemStockDto;
import com.sparta.productservice.product.dto.request.HoldItemStockRequestDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
  private final RedissonClient redissonClient;

  private final String STOCK_PREFIX = "OPTION_ITEM_ID:";
  private final String STOCK_LOCK = "STOCK_LOCK";

  @Override
  public ApiResponse getProducts(String sortBy, int page, int size, boolean isAsc,
      Category category) {
    Direction direction = isAsc ? Direction.ASC : Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<ProductSummaryDto> products = productRepository.findProducts(pageable, category);

    return ApiResponseUtil.createSuccessResponse("Products loaded successfully.", products);
  }

  @Override
  public ApiResponse getProduct(Long productId) {
    Product product = findProduct(productId);
    List<ProductImage> productImageList = findProductImageList(product);
    List<ProductOptionInfoDto> productOptionList = findProductOptionList(productId);
    ProductInfoDto productInfo = ProductInfoDto.of(product, productImageList, productOptionList);

    return ApiResponseUtil.createSuccessResponse("Product loaded successfully.", productInfo);
  }

  @Override
  public ApiResponse getProductOptions(Long productId) {
    List<ProductOptionInfoDto> productOptionList = findProductOptionList(productId);

    return ApiResponseUtil.createSuccessResponse("Product option list loaded successfully.", productOptionList);
  }

  @Override
  public OptionItemDto findOptionItem(Long optionItemId) {
    Optional<OptionItem> optionItem = optionItemRepository.findById(optionItemId);

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
    String key = STOCK_PREFIX + optionItemId;

    String stockValue = stockRepository.getValueByKey(key);
    int stock;
    if (stockValue == null || Integer.parseInt(stockValue) == 0) {
      OptionItem optionItem = optionItemRepository.findById(optionItemId)
          .orElseThrow(() -> CustomException.from(ExceptionCode.OPTION_ITEM_NOT_FOUND));

      stock = optionItem.getStock();

      stockRepository.setValue(key, String.valueOf(stock), Duration.ofMinutes(3));
    } else {
      stock = Integer.parseInt(stockValue);
    }

    ItemStockDto itemStockDto = ItemStockDto.of(optionItemId, stock);
    return ApiResponseUtil.createSuccessResponse("The stock of the OptionItem "+optionItemId + " loaded successfully.", itemStockDto);
  }

  @Override
  public ApiResponse holdStock(HoldItemStockRequestDto requestDto) {
    List<HoldItemStockDto> successDecreaseStock = new ArrayList<>();

    RLock lock = redissonClient.getLock(STOCK_LOCK);

    try {
      // Lock 확득 시도
      if (lock.tryLock(1000, 30, TimeUnit.SECONDS)) {
        for (HoldItemStockDto item : requestDto.getHoldItemStockDtoList()) {
          String key = STOCK_PREFIX + item.getOptionItemId();
          String stockValue = stockRepository.getValueByKey(key);
          int stock;
          if (stockValue == null || Integer.parseInt(stockValue) == 0) {
            OptionItem optionItem = optionItemRepository.findById(item.getOptionItemId())
                .orElseThrow(() -> CustomException.from(ExceptionCode.OPTION_ITEM_NOT_FOUND));

            stock = optionItem.getStock();

            stockRepository.setValue(key, String.valueOf(stock), Duration.ofMinutes(3));
          } else {
            stock = Integer.parseInt(stockValue);
          }

          if (stock < item.getQuantity()) {
            throw CustomException.from(ExceptionCode.OUT_OF_STOCK);
          }

          stockRepository.decreaseValue(key, item.getQuantity());
          successDecreaseStock.add(item);
        }
      } else {
        throw CustomException.from(ExceptionCode.LOCK_ACQUISITION_FAILED);
      }
    } catch (CustomException e) {
      // 재고 복구
      for (HoldItemStockDto item : successDecreaseStock) {
        String key = STOCK_PREFIX + item.getOptionItemId();
        stockRepository.increaseValue(key, item.getQuantity());
      }
      throw e;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      // 락 해제
      lock.unlock();
    }
    return ApiResponseUtil.createSuccessResponse("Decrease stock successfully.", null);
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
