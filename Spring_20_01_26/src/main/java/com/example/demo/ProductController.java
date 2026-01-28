package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

//@RestController
@Controller //whenever you want to attach ui
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final String UPLOAD_DIR = "uploads/";

    public ProductController(ProductService productService) {
        this.productService = productService;
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            System.err.println("Could not create upload directory: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestParam String name,
                                 @RequestParam Double price,
                                 @RequestParam("imageInput") MultipartFile file) throws IOException {

        // Temporarily save to get an ID or use a placeholder
        Product product = new Product(name, price, null);
        Product savedProduct = productService.createProduct(product);

        if (file != null && !file.isEmpty()) {
            String extension = getFileExtension(file.getOriginalFilename());
            // Requirement: product-id.filetype
            String filename = "product-" + savedProduct.getId() + "." + extension;
            
            saveFile(file, filename);
            savedProduct.setImageName(filename);
            productService.updateProduct(savedProduct); // Update with actual filename
        }

        return savedProduct;
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "imageFile", required = false) MultipartFile file) throws IOException {

        Product product = productService.getProductById(id); // Ensure you have this method in Service
        product.setName(name);
        product.setPrice(price);

        if (file != null && !file.isEmpty()) {
            String extension = getFileExtension(file.getOriginalFilename());
            String filename = "product-" + id + "." + extension;
            
            saveFile(file, filename);
            product.setImageName(filename);
        }

        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/images/{filename:.+}")
    public byte[] getImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(UPLOAD_DIR + filename);
        return Files.readAllBytes(path);
    }

    // --- Helper Methods ---
    
    private void saveFile(MultipartFile file, String filename) throws IOException {
        Path path = Paths.get(UPLOAD_DIR + filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "jpg";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    //Update product
    //@GetMapping("/updateproduct")
//    public String updateProduct(Model model)
//    {
//    	model.addAttribute("allproducts", getAllProducts());
//    	return "updateproduct";
//    }
//    @GetMapping("/updateproductform")
//    public String updateProductform(Model model) {
//    	Product product = new Product();
//    	model.addAttribute("product",product);
//    	return "updateproductform";
//    }
//    @PostMapping("/saveupdateproduct")
//    
    
}