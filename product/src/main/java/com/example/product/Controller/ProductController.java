package com.example.product.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.product.Service.ProductService;
import com.example.product.dto.ProductRequest;
import com.example.product.dto.ProductResponse;

import lombok.AllArgsConstructor;

/**
 *
 * @author maith
 */

@Controller
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        return "product-form";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return "redirect:/api/product/all";
    }

    @GetMapping("/all")
    public String getAllProducts(Model model) {
        List<ProductResponse> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        ProductResponse product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "product-update-form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
        return "redirect:/api/product/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/api/product/all";
    }

    @GetMapping("/view/{id}")
    public String getProduct(@PathVariable String id, Model model) {
        ProductResponse product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "product-view";
    }
}
