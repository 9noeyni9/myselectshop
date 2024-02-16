package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.createProduct(productRequestDto,userDetails.getUser());
    }

    @PutMapping("products/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto productMypriceRequestDto){
        return productService.updateProduct(id, productMypriceRequestDto);
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProductList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.getProductList(userDetails.getUser(), page-1, size, sortBy, isAsc);
        // page-1  : client에선 1부터 시작하고 Pagable PageRequest에선 0부터 시작하기 때문에 client에서 넘어온 값에 -1해줘야 둘이 일치함
    }

    @PostMapping("/products/{productId}/folder")
    public void addFolder(
            @PathVariable Long productId,
            @RequestParam Long folderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        productService.addFolder(productId, folderId, userDetails.getUser());

    }

    @GetMapping("/folders/{folderId}/products")
    public Page<ProductResponseDto> getProductsInFolder(
            @PathVariable Long folderId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.getProductsInFolders(
                folderId,
                page-1,
                size,
                sortBy,
                isAsc,
                userDetails.getUser()
        );
    }

}
