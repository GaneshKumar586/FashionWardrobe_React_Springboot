package com.gana.repository;

import com.gana.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    public Order findOrderById(Long orderId);

    @Query("SELECT o FROM Order o WHERE o.userId=:userId AND (o.orderStatus= 'PLACED' OR o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'DELIVERED' OR o.orderStatus = 'SHIPPED')")
    public List<Order> getUserOrders(@Param("userId") Long userId);
}
