package com.example.demo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}
	@Override
	public Product getProductById(Integer id) {
		// TODO Auto-generated method stub
		return productRepository.getById(id);
	}
	@Override
	public List<Product> getAllProducts() {
	    List<Product> products = productRepository.findAll();

	    // Print each product to the console
	    products.forEach(product -> System.out.println(product));

	    // Return the list
	    return products;
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}
	@Override
	public void deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}
}

