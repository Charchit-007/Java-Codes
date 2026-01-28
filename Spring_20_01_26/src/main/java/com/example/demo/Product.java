package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    private String imageName; // store filename (if using uploads folder)

    // --- Constructors ---
    public Product() {}

    public Product(String name, Double price, String imageName) {
        this.name = name;
        this.price = price;
        this.imageName = imageName;
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + '\'' + ", price=" + price + ", imageName='" + imageName + '\'' + '}';
    }
}
