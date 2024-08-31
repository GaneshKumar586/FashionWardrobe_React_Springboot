package com.gana.controllers;

import com.gana.Service.OrderService;
import com.gana.Service.UserService;
import com.gana.exception.OrderException;
import com.gana.exception.UserException;
import com.gana.model.Address;
import com.gana.model.Order;
import com.gana.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<Order>createOrder(@RequestBody Address UserShipAddress, @RequestHeader("Authorization") String jwt) throws UserException{
        User user=userService.findUserByJwt(jwt);
        Order order = orderService.createOrder(user,UserShipAddress);
        System.out.println("order"+order);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserByJwt(jwt);
        List<Order> orders  = orderService.userOrdersHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,@RequestHeader("Authorization") String jwt) throws UserException, OrderException{
        User user = userService.findUserByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }
}
