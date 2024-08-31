package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Category;
import com.gana.model.Product;
import com.gana.repository.CategoryRepository;
import com.gana.repository.ProductRepository;
import com.gana.request.CreateProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductServiceImplementation implements ProductService{


    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;


    @Override
    public Product createProduct(CreateProductRequest createProductRequest){
        Category section = categoryRepository.findByName(createProductRequest.getCategoryStage1());

        if(section == null){
            Category newSection = new Category();
            newSection.setName(createProductRequest.getCategoryStage1());
            newSection.setStage(1);

            section = categoryRepository.save(newSection);
        }

        Category subsection = categoryRepository.findByNameAndParent(createProductRequest.getCategoryStage2(),section.getName());
        if(subsection == null){
            Category newsubSection = new Category();
            newsubSection.setName(createProductRequest.getCategoryStage1());
            newsubSection.setStage(2);
            newsubSection.setParentCategory(section);

            subsection = categoryRepository.save(newsubSection);
        }

        Category subsubsection = categoryRepository.findByNameAndParent(createProductRequest.getCategoryStage3(),subsection.getName());
        if(subsubsection == null){
            Category newsubsubSection = new Category();
            newsubsubSection.setName(createProductRequest.getCategoryStage1());
            newsubsubSection.setStage(3);
            newsubsubSection.setParentCategory(subsection);

            subsubsection = categoryRepository.save(newsubsubSection);
        }

        Product newProduct = new Product();
        newProduct.setTitle(createProductRequest.getTitle());
        newProduct.setCreatedAt(LocalDateTime.now());
        newProduct.setCategory(subsubsection);
        newProduct.setColor(createProductRequest.getColor());
        newProduct.setBrand(createProductRequest.getBrand());
        newProduct.setActualPrice(createProductRequest.getActualPrice());
        newProduct.setDescription(createProductRequest.getDescription());
        newProduct.setDetails(createProductRequest.getDetails());
        newProduct.setDiscountPercent(createProductRequest.getDiscountPercent());
        newProduct.setImage(createProductRequest.getImage());
        newProduct.setPrice(createProductRequest.getPrice());
        newProduct.setQuantity(createProductRequest.getQuantity());
        newProduct.setSizes(createProductRequest.getSizes());

        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }


    @Override
    public String deleteProduct(Long productId) throws ProductException{
        Product product = findProductById(productId);
        product.getSizes().clear();
        if(product !=null){
            productRepository.delete(product);
            return "product deleted successfully";

        }
        throw new  ProductException("Product does not exists");

    }


    @Override
    public Product updateProduct(Long productId, Product updatedProduct) throws ProductException{
        Product actualProduct = findProductById(productId);
        actualProduct.setTitle(updatedProduct.getTitle());
//        actualProduct.setCreatedAt(LocalDateTime.now());
//        actualProduct.setCategory(subsubsection);
        actualProduct.setColor(updatedProduct.getColor());
        actualProduct.setBrand(updatedProduct.getBrand());
        actualProduct.setActualPrice(updatedProduct.getActualPrice());
        actualProduct.setDescription(updatedProduct.getDescription());
        actualProduct.setDetails(updatedProduct.getDetails());
        actualProduct.setDiscountPercent(updatedProduct.getDiscountPercent());
        actualProduct.setImage(updatedProduct.getImage());
        actualProduct.setPrice(updatedProduct.getPrice());
        actualProduct.setQuantity(updatedProduct.getQuantity());
        actualProduct.setSizes(updatedProduct.getSizes());

        return productRepository.save(actualProduct);


    }


    @Override
    public Product findProductById(Long id) throws ProductException{

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new ProductException("Product not Found with the id"+id);
    }


    @Override
    public List<Product> findProductByCategory(String category){
        return null;
    }


    @Override
    public Page<Product> getAllProduct(String category, List<String>colors, List<String>sizes, Integer minPrice, Integer maxPrice,
                                       Integer minDiscount, String sortType, String stockStatus, Integer pageNumber, Integer pageSize){

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        List<Product> products = productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sortType);

        if(!colors.isEmpty()){
            products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());

        }
        if(stockStatus!=null){
            if(stockStatus.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            } else if (stockStatus.equals("out_of_stock")) {
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex,endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable, products.size());
        return filteredProducts;

    }

}
