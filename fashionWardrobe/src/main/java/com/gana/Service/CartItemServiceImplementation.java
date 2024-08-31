package com.gana.Service;

import com.gana.exception.CartItemException;
import com.gana.exception.UserException;
import com.gana.model.Cart;
import com.gana.model.CartItem;
import com.gana.model.Product;
import com.gana.model.User;
import com.gana.repository.CartItemRepository;
import com.gana.repository.CartRepository;
import com.gana.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemServiceImplementation implements CartItemService{
//    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private UserService userService;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
//        Product product = cartItem.getProduct(cartItem.get)
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getPrice() * cartItem.getQuantity());
        cartItem.setDiscountPrice(cartItem.getDiscountPrice() * cartItem.getQuantity());

        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getPrice() * item.getQuantity());
            item.setDiscountPrice(item.getDiscountPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartitemExist(cart,product,size,userId);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem= findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());

        User userRequested = userService.findUserById(userId);

        if(user.getId().equals(userRequested.getId())){
            cartItemRepository.deleteById(cartItemId);
        }
        else{
            throw new UserException("you can't remove other users items");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with the id "+cartItemId);
    }
}
