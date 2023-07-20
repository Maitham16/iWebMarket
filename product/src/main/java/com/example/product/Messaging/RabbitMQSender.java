// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.product.Messaging;

import com.example.product.Model.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
   private final RabbitTemplate rabbitTemplate;

   public RabbitMQSender(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
   }

   public void sendProduct(Product product, String operation) {
      String massage = operation + " " + product.getId();
      this.rabbitTemplate.convertAndSend("productExchange", "productRoutingKey", massage);
   }
}
