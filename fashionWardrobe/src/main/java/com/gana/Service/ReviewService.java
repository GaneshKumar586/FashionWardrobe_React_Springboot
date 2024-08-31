package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Review;
import com.gana.model.User;
import com.gana.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException;

    public List<Review> getAllReviews(Long productId);
}
