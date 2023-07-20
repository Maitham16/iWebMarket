// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.product.Messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
   public RabbitMQConfig() {
   }

   @Bean
   public ConnectionFactory connectionFactory() {
      CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
      connectionFactory.setUsername("guest");
      connectionFactory.setPassword("guest");
      return connectionFactory;
   }

   @Bean
   public RabbitTemplate rabbitTemplate() {
      return new RabbitTemplate(this.connectionFactory());
   }

   @Bean
   public TopicExchange productExchange() {
      return new TopicExchange("productExchange");
   }

   @Bean
   public Queue productQueue() {
      return new Queue("productQueue");
   }

   @Bean
   public Binding binding() {
      return BindingBuilder.bind(this.productQueue()).to(this.productExchange()).with("productRoutingKey");
   }
}
