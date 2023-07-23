package com.example.order.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
