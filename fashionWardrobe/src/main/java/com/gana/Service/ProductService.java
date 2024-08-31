package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Product;
import com.gana.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest createProductRequest);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product updatedProduct) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category, List<String>colors, List<String>sizes, Integer minPrice,Integer maxPrice,
                                       Integer minDiscount, String sortType, String stockStatus, Integer pageNumber, Integer pageSize);
}
