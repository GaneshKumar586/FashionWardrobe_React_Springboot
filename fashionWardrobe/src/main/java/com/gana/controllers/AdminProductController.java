package com.gana.controllers;

import com.gana.Service.OrderService;
import com.gana.Service.ProductService;
import com.gana.exception.OrderException;
import com.gana.exception.ProductException;
import com.gana.model.Order;
import com.gana.model.Product;
import com.gana.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<Product> CreateProductHandler(@RequestBody CreateProductRequest adminReq){
        Product product=productService.createProduct(adminReq);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> DeleteProductHandler(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) {

        try{
            productService.deleteProduct(productId);
            return new ResponseEntity<String>("product deletion successfull", HttpStatus.OK);
        }
        catch (ProductException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/update/{productId}")
    public ResponseEntity<Product> UpdateProductHandler(@PathVariable Long productId,@RequestBody CreateProductRequest adminReq) throws ProductException{

        try{
            Product oldProduct = productService.findProductById(productId);
            Product newproduct=productService.updateProduct(productId,oldProduct);
            return new ResponseEntity<Product>(newproduct, HttpStatus.CREATED);
        }
        catch (ProductException e) {
            throw new  ProductException("Product does not exists");
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
//    @GetMapping("/all")
//    public ResponseEntity<List<Product>> findAllProduct(){
//        List<Product> product=productService.getAllProduct();
//        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
//    }

    @PostMapping("/multicreate")
    public ResponseEntity<String> createMultipleProduct(@RequestBody CreateProductRequest[] productRequests){
        for(CreateProductRequest product:productRequests){
            productService.createProduct(product);
        }
        return new ResponseEntity<String>("Product deleted successfully",HttpStatus.CREATED);
    }


}
