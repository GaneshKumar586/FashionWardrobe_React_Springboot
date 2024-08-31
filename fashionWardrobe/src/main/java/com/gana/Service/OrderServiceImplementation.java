package com.gana.Service;

import com.gana.exception.OrderException;
import com.gana.model.*;
import com.gana.repository.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderServiceImplementation implements OrderService{
    private UserRepository userRepository;
//    private CartRepository cartRepository;
//    private ProductService productService;
    private CartService cartService;
    private OrderRepository orderRepository;
    private AddressRespository addressRespository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;
    @Override
    public Order createOrder(User user, Address destShipAddress){
        destShipAddress.setUser(user);
        Address address = addressRespository.save(destShipAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item:cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountPrice((int) item.getDiscountPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setShippingAddress(destShipAddress);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalPriceAfterDiscount((int) cart.getTotalDiscountedPrice());
        createdOrder.setDiscount((int) cart.getDiscount());
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setDeliveryDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for(OrderItem item:orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }
    @Override
    public List<Order> userOrdersHistory(Long userId){
        List<Order> orders = orderRepository.getUserOrders(userId);
        return orders;
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException{
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException{
        Optional<Order> opt = orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("order not exist with id"+orderId);
    }

    @Override
    public Order placeOrder(Long orderId)throws OrderException{
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmOrder(Long orderId)throws OrderException{
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order shippedOrder(Long orderId)throws OrderException{
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order deliveredOrder(Long orderId)throws OrderException{
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order cancelledOrder(Long orderId)throws OrderException{
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }
}
