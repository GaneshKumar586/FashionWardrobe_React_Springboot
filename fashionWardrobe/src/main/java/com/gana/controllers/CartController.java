package com.gana.controllers;

import com.gana.Service.CartService;
import com.gana.Service.UserService;
import com.gana.exception.ProductException;
import com.gana.exception.UserException;
import com.gana.model.Cart;
import com.gana.model.User;
import com.gana.request.AddItemRequest;
import com.gana.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserByJwt(jwt);
        cartService.addCardItem(user.getId(),req);
        ApiResponse res = new ApiResponse();
        res.setMessage("item Added to the Cart");
        res.setStatus(true);

        return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
    }
}
