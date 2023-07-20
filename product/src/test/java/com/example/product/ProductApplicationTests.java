package com.example.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import com.example.product.Repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApplicationTests {

	// mongo db container
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.3");

	// MockMvc
	@Autowired
	private MockMvc mockMvc;

	// product repository
	@Autowired
	private ProductRepository productRepository;

	static {
		mongoDBContainer.start();
	}

	// dynamic property
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
				.param("name", "Item 1")
				.param("description", "description description description description description description description description description description description description description description description description description description description description description")
				.param("price", "1200")
				.param("image", "link")
				.param("category", "Category 1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/api/product/all"));
		Assertions.assertEquals(1, productRepository.findAll().size());
	}
	
}