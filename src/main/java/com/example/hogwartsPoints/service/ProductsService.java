package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterProductDTO;
import com.example.hogwartsPoints.dto.update.UpdateProductDTO;
import com.example.hogwartsPoints.entity.ProductEntity;
import com.example.hogwartsPoints.respository.ProductsRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.example.hogwartsPoints.utils.AppUtils.copyNonNullProperties;

import java.util.Objects;

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