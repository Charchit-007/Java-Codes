package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductPageController {

    @Autowired
    private ProductServiceImpl productService;

    // -----------------------------
    // LOAD PAGE
    // -----------------------------
    @GetMapping
    public String productsPage(Model model) {

        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());

        return "products";
    }

    // -----------------------------
    // CREATE
    // -----------------------------
    @PostMapping("/save")
    public String saveProduct(Product product,
                               RedirectAttributes ra) {

        productService.createProduct(product);
        ra.addFlashAttribute("success", "Product created successfully");

        return "redirect:/products";
    }

    // -----------------------------
    // UPDATE (FORM BASED)
    // -----------------------------
    @PostMapping("/update")
    public String updateProduct(Product product,
                                 RedirectAttributes ra) {

        productService.updateProduct(product);
        ra.addFlashAttribute("success", "Product updated successfully");

        return "redirect:/products";
    }

    // -----------------------------
    // DELETE (LINK / BUTTON)
    // -----------------------------
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id,
                                 RedirectAttributes ra) {

        productService.deleteProduct(id);
        ra.addFlashAttribute("success", "Product deleted successfully");

        return "redirect:/products";
    }
}
