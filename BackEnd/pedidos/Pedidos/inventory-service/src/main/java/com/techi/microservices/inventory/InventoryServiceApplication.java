package com.techi.microservices.inventory;

import com.techi.microservices.inventory.model.Product;
import com.techi.microservices.inventory.repository.ProductRepository;
import com.techi.microservices.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

//		Product product = Product.builder()
//				.productId(1L)
//				.name("desodorante")
//				.stock(24)
//				.build();
//
//		Product product2 = Product.builder()
//				.productId(2L)
//				.name("shampoo")
//				.stock(35)
//				.build();
//
//		productRepository.save(product);
//		productRepository.save(product2);


	}
}
