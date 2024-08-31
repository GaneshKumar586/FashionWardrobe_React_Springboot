package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Product;
import com.gana.model.Rating;
import com.gana.model.User;
import com.gana.repository.RatingRepository;
import com.gana.request.RatingRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RatingServiceImplementation implements RatingService{
    private RatingRepository ratingRepository;
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest ratingRequest, User user) throws ProductException {
        Product product = productService.findProductById(ratingRequest.getProductId());
        Rating newRating = new Rating();
        newRating.setRating(ratingRequest.getRating());
        newRating.setProduct(product);
        newRating.setUser(user);
        newRating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(newRating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {

        return ratingRepository.getAllProductsRating(productId);
    }
}
