package com.example.myamazon;

public class ProductCart {
    private int id;
    private String name;
    private String image;
    private String description;
    private double price;
    private int quantity;

    public ProductCart(int id, String name, String image, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
