package com.sparta.productservice.product.repository;

import com.sparta.productservice.product.entity.OptionItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

  Optional<OptionItem> findByProductIdAndProductOptionId(Long productId, Long productOptionId);

}
