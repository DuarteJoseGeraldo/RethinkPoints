package com.example.RethinkPoints.service;

import com.example.RethinkPoints.dto.MessagesDTO;
import com.example.RethinkPoints.dto.register.RegisterProductDTO;
import com.example.RethinkPoints.dto.update.UpdateProductDTO;
import com.example.RethinkPoints.entity.ProductEntity;
import com.example.RethinkPoints.respository.ProductsRepository;
import com.example.RethinkPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.example.RethinkPoints.utils.AppUtils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final JwtUtil jwtUtil;
    private final ProductsRepository productsRepo;

    public ProductEntity getProductDataByCode(String accessToken, String code) throws Exception {
        jwtUtil.userTokenValidator(accessToken);
        return productsRepo.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public ProductEntity registerProduct(String accessToken, RegisterProductDTO productData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        validateRegisterProductData(productData);
        return productsRepo.save(ProductEntity.builder()
                .code(productData.getCode())
                .name(productData.getName())
                .price(productData.getPrice())
                .points(productData.getPoints()).build());
    }

    public ProductEntity updateProduct(String accessToken, Long id, UpdateProductDTO newData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        ProductEntity product = productsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
        if (newData.getPrice() == 0) newData.setPrice(product.getPrice());
        if (newData.getPoints() == 0) newData.setPoints(product.getPoints());
        copyNonNullProperties(newData, product);
        return productsRepo.save(product);
    }

    public MessagesDTO deleteProduct(String accessToken, Long id) throws Exception {
        jwtUtil.adminValidator(accessToken);
        productsRepo.deleteById(id);
        return MessagesDTO.builder().message("Product deleted successfully").build();
    }

    private void validateRegisterProductData(RegisterProductDTO product) {
        if (product.getPrice() < 0) throw new IllegalArgumentException("Value should be greater than 0");
        if (product.getPoints() < 0) throw new IllegalArgumentException("Points should be greater than 0");
    }
}