/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

/**
 *
 * @author dusht
 */
public class Product {
    
    private String productID;
    private String name;
    private String category;
    private int quantity;
    private double price;

    public Product(String productID, String name, String category, int quantity, double price) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
    
    // ---------- necessary to for edit the names, quantity and prices. * i havent tried or wrritten bout the id's tho ---------//
    
    public void setProductID(String productid) {
        this.productID = productid;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setProductName(String name) {
      this.name = name;
    }
    
    public void setPrice(double price) {
      this.price = price;
    }
    
    public void setCategory(String category) {
    this.category = category;
   }
 
}
