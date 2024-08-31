package com.gana.Service;


import com.gana.exception.OrderException;
import com.gana.model.Address;
import com.gana.model.Order;
import com.gana.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address destShipAddress);
    public List<Order> userOrdersHistory(Long userId);
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
    public Order findOrderById(Long orderId) throws OrderException;
    public Order placeOrder(Long orderId)throws OrderException;
    public Order confirmOrder(Long orderId)throws OrderException;
    public Order shippedOrder(Long orderId)throws OrderException;
    public Order deliveredOrder(Long orderId)throws OrderException;
    public Order cancelledOrder(Long orderId)throws OrderException;

}
