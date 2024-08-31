package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Product;
import com.gana.model.Review;
import com.gana.model.User;
import com.gana.repository.ProductRepository;
import com.gana.repository.ReviewRepository;
import com.gana.request.ReviewRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReviewServiceImplementation implements ReviewService{

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;
    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product= productService.findProductById(reviewRequest.getProductId());

        Review newReview =new Review();
        newReview.setCreatedAt(LocalDateTime.now());
        newReview.setReview(reviewRequest.getReview());
        newReview.setUser(user);
        newReview.setProduct(product);

        return reviewRepository.save(newReview);
    }

    @Override
    public List<Review> getAllReviews(Long productId) {

        return reviewRepository.getAllProductsReview(productId);
    }
}
