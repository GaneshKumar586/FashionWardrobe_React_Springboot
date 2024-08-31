package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Rating;
import com.gana.model.User;
import com.gana.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest ratingRequest, User user) throws ProductException;
    public List<Rating> getProductRating(Long productId);

}
