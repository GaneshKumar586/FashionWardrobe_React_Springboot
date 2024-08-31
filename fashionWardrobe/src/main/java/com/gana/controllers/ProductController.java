package com.gana.controllers;

import com.gana.Service.ProductService;
import com.gana.exception.ProductException;
import com.gana.model.Product;
import com.gana.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam String category, @RequestParam List<String> color, @RequestParam List<String> size, @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize
            ) {

        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        System.out.println("complete Products");
        return new ResponseEntity<Page<Product>>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
    }
//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String searchValue){
//        List<Product> products = productService.searchProduct(searchValue);
//        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//    }
//    @PostMapping("/products/create")
//    public ResponseEntity<Product> createProductByAdminHandler(CreateProductRequest createProductRequest){
//        Product productCreated = productService.createProduct(createProductRequest);
//        System.out.println("Product Created successfully");
//        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/products/delete/{productId}")
//    public ResponseEntity<String> createProductByAdminHandler( @PathVariable Long productid){
//        String productDeleteMessage = productService.deleteProduct(productid);
//        System.out.println("Product Created successfully");
//        return new ResponseEntity<String>(productDeleteMessage, HttpStatus.OK);
//    }
//
//    @PostMapping("/products/update/{productId")
//    public ResponseEntity<Product> updateProductByAdminHandler( @PathVariable Long productId,CreateProductRequest updateProductRequest){
//        Product productUpdated = productService.updateProduct(productId, updateProductRequest);
//        System.out.println("Product Updated successfully");
//        return new ResponseEntity<>(productUpdated, HttpStatus.OK);
//    }
}
