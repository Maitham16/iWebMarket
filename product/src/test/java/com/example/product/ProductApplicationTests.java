package com.example.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.product.Repository.ProductRepository;
import com.example.product.dto.ProductRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApplicationTests {

	// mongo db container
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.3");

	// MockMvc
	@Autowired
	private MockMvc mockMvc;

	// object mapper
	@Autowired
	private ObjectMapper objectMapper;

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
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Item 1")
				.description("description description description description description description description description description description description description description description description description description description description description description  ")
				.price(1200)
				.image("link")
				.category("Category 1")
				.build();
	}
}