package com.example.product.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.product.Model.Product;

/**
 *
 * @author maith
 */
public interface ProductRepository extends MongoRepository<Product, String> {

}
