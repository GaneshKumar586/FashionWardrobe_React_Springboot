package com.gana.controllers;

import com.gana.Service.ReviewService;
import com.gana.Service.UserService;
import com.gana.exception.ProductException;
import com.gana.exception.UserException;
import com.gana.model.Review;
import com.gana.model.User;
import com.gana.request.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserByJwt(jwt);
        Review createdReview = reviewService.createReview(reviewRequest,user);
        return new ResponseEntity<Review>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productid}")
    public ResponseEntity<List<Review>>  getAllReviewsOfProduct(@PathVariable Long productid, @RequestHeader("Authorization") String jwt) throws UserException,ProductException{
        User user = userService.findUserByJwt(jwt);
        List<Review> productReviews = reviewService.getAllReviews(productid);
        return new ResponseEntity<List<Review>>(productReviews,HttpStatus.ACCEPTED);
    }
}
