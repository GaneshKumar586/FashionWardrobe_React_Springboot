package com.gana.Service;

import com.gana.exception.ProductException;
import com.gana.model.Cart;
import com.gana.model.CartItem;
import com.gana.model.Product;
import com.gana.model.User;
import com.gana.repository.CartItemRepository;
import com.gana.repository.CartRepository;
import com.gana.request.AddItemRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartServiceImplementation implements CartService{

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCardItem(Long userId, AddItemRequest addItemRequest) throws ProductException {
        Cart cart  = cartRepository.findUserById(userId);
        Product product = productService.findProductById(addItemRequest.getProductId());
        CartItem isPresent = cartItemService.isCartItemExist(cart,product,addItemRequest.getSize(),userId);
        if(isPresent==null){
            CartItem cartItem=new CartItem();

            cartItem.setProduct(product);
            cartItem.setQuantity(addItemRequest.getQuantity());
            cartItem.setSize(addItemRequest.getSize());
            cartItem.setUserId(userId);
            cartItem.setPrice(addItemRequest.getQuantity()*product.getPrice());
            cartItem.setCart(cart);

            CartItem cartItemCreated = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(cartItemCreated);
        }
        return "Item has been added to the cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart=cartRepository.findUserById(userId);
        double totalPrice = 0;
        double totalDiscountPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem:cart.getCartItems()){
            totalPrice +=cartItem.getPrice();
            totalDiscountPrice += cartItem.getDiscountPrice();
            totalItem +=cartItem.getQuantity();
        }
        cart.setTotalItems(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountPrice);
        cart.setDiscount(totalPrice-totalDiscountPrice);
        return cartRepository.save(cart);
    }
}
