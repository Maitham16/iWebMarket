// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.product.Service;

import com.example.product.Messaging.RabbitMQSender;
import com.example.product.Model.Product;
import com.example.product.Repository.ProductRepository;
import com.example.product.dto.ProductRequest;
import com.example.product.dto.ProductResponse;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
   private static final Logger log = LoggerFactory.getLogger(ProductService.class);
   private final ProductRepository productRepository;
   private final RabbitMQSender rabbitMQSender;

   public void createProduct(ProductRequest productRequest) {
      Product product = Product.builder().id(productRequest.getId()).name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).category(productRequest.getCategory()).image(productRequest.getImage()).build();
      this.productRepository.save(product);
      this.rabbitMQSender.sendProduct(product, "Created");
      log.info("RabbitMQ Created Message has been sent");
      log.info("Product created: {}", product.getId());
   }

   public List<ProductResponse> getAllProducts() {
      List<Product> products = this.productRepository.findAll();
      return products.stream().map(this::mapToProductResponse).toList();
   }

   private ProductResponse mapToProductResponse(Product product) {
      return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).price(product.getPrice()).category(product.getCategory()).image(product.getImage()).build();
   }

   public void updateProduct(String id, ProductRequest productRequest) {
      Optional<Product> product = this.productRepository.findById(id);
      if (product.isPresent()) {
         ((Product)product.get()).setName(productRequest.getName());
         ((Product)product.get()).setDescription(productRequest.getDescription());
         ((Product)product.get()).setPrice(productRequest.getPrice());
         ((Product)product.get()).setCategory(productRequest.getCategory());
         ((Product)product.get()).setImage(productRequest.getImage());
         this.productRepository.save((Product)product.get());
         this.rabbitMQSender.sendProduct((Product)product.get(), "Updated");
         log.info("RabbitMQ Updated Message has been sent");
         log.info("Product updated: {}", ((Product)product.get()).getId());
      } else {
         throw new RuntimeException("Product not found with ID: " + id);
      }
   }

   public void deleteProduct(String id) {
      Optional<Product> product = this.productRepository.findById(id);
      if (product.isPresent()) {
         this.productRepository.deleteById(id);
         this.rabbitMQSender.sendProduct((Product)product.get(), "Deleted");
         log.info("RabbitMQ Deleted Message has been sent");
         log.info("Product deleted: {}", id);
      } else {
         throw new RuntimeException("Product not found with ID: " + id);
      }
   }

   public ProductResponse getProduct(String id) {
      Optional<Product> product = this.productRepository.findById(id);
      if (product.isPresent()) {
         return this.mapToProductResponse((Product)product.get());
      } else {
         throw new RuntimeException("Product not found with ID: " + id);
      }
   }

   public ProductService(final ProductRepository productRepository, final RabbitMQSender rabbitMQSender) {
      this.productRepository = productRepository;
      this.rabbitMQSender = rabbitMQSender;
   }
}
