package com.example.product.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.Service.ProductService;
import com.example.product.dto.ProductRequest;
import com.example.product.dto.ProductResponse;

import lombok.AllArgsConstructor;

/**
 *
 * @author maith
 */

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    final private ProductService productService;

    // Create a new product
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    // Get all products
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // update a product
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(String id, @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
    }

    // delete a product
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(String id){
        productService.deleteProduct(id);
    }

    // get a product by id
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(String id){
        return productService.getProduct(id);
    }
}
