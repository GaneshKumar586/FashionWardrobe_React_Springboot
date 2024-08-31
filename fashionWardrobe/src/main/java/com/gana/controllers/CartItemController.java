package com.gana.controllers;

import com.gana.Service.CartItemService;
import com.gana.Service.UserService;
import com.gana.exception.CartItemException;
import com.gana.exception.UserException;
import com.gana.model.CartItem;
import com.gana.model.User;
import com.gana.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user = userService.findUserByJwt(jwt);
        cartItemService.removeCartItem(user.getId(),cartItemId);

        ApiResponse res= new ApiResponse();
        res.setMessage("item deleted to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItem(@RequestBody CartItem cartItem,@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user = userService.findUserByJwt(jwt);
        CartItem cartItemUpdated = cartItemService.updateCartItem(user.getId(),cartItemId,cartItem);

        return new ResponseEntity<>(cartItemUpdated, HttpStatus.OK);
    }
}
