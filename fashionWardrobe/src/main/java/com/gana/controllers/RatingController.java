package com.gana.controllers;

import com.gana.Service.RatingService;
import com.gana.Service.UserService;
import com.gana.exception.ProductException;
import com.gana.exception.UserException;
import com.gana.model.Rating;
import com.gana.model.User;
import com.gana.request.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserByJwt(jwt);
        Rating createdRating = ratingService.createRating(ratingRequest,user);
        return new ResponseEntity<Rating>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productid}")
    public ResponseEntity<List<Rating>>  getAllRatingsOfProduct(@PathVariable Long productid, @RequestHeader("Authorization") String jwt) throws UserException,ProductException{
        User user = userService.findUserByJwt(jwt);
        List<Rating> productRatings = ratingService.getProductRating(productid);
        return new ResponseEntity<List<Rating>>(productRatings,HttpStatus.ACCEPTED);
    }
}
