package com.example.RethinkPoints.controller;

import com.example.RethinkPoints.dto.MessagesDTO;
import com.example.RethinkPoints.dto.register.RegisterProductDTO;
import com.example.RethinkPoints.dto.update.UpdateProductDTO;
import com.example.RethinkPoints.entity.ProductEntity;
import com.example.RethinkPoints.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static com.example.RethinkPoints.utils.AppUtils.getRequestToken;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping(value ="/data")
    public ResponseEntity<ProductEntity> getProductData(HttpServletRequest request, @RequestParam String code) throws Exception {
        return ResponseEntity.ok(productsService.getProductDataByCode(getRequestToken(request), code));
    }

    @PostMapping(value ="/register")
    public ResponseEntity<ProductEntity> registerProduct(HttpServletRequest request, @RequestBody RegisterProductDTO productData) throws Exception {
        return ResponseEntity.created(URI.create("/products/register")).body(productsService.registerProduct(getRequestToken(request), productData));
    }

    @PatchMapping(value ="/update")
    public ResponseEntity<ProductEntity> updateProduct(HttpServletRequest request, @RequestParam Long id, @RequestBody UpdateProductDTO newProductData) throws Exception {
        return ResponseEntity.ok(productsService.updateProduct(getRequestToken(request), id, newProductData));
    }

    @DeleteMapping(value ="/delete")
    public ResponseEntity<MessagesDTO> deleteProduct(HttpServletRequest request, @RequestParam Long id) throws Exception{
        return ResponseEntity.ok(productsService.deleteProduct(getRequestToken(request), id));
    }
}
