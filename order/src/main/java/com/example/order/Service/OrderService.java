package com.example.order.Service;

import com.example.order.Model.Order;
import com.example.order.Model.OrderItems;
import com.example.order.Repository.OrderRepository;
import com.example.order.dto.OrderItemsDto;
import com.example.order.dto.OrderRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItemsDto> orderItemDtoList = orderRequest.getOrderItemDtoList();
        if (orderItemDtoList != null) {
            List<OrderItems> orderItems = orderItemDtoList
                .stream()
                .map(this::mapToDto)
                .toList();

            order.setOrderItems(orderItems);
        }

        // save in db
        orderRepository.save(order);
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setSkucode(orderItemsDto.getSkucode());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        return orderItems;
    }

}

