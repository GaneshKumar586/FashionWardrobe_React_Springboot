package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Cart;
import com.gana.model.User;
import com.gana.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCardItem(Long userId, AddItemRequest addItemRequest) throws ProductException;

    public Cart findUserCart(Long userId);
}
