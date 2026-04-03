/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

/**
 *
 * @author dusht
 */
public class Sale {
    
    private int saleId;
    private String productName;
    private int quantitySold;
    private double priceEach;
    private double total;
    private String saleDate;

    public Sale(int saleId, String productName, int quantitySold, double priceEach, double total, String saleDate) {
        this.saleId      = saleId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.priceEach   = priceEach;
        this.total       = total;
        this.saleDate    = saleDate;
    }

    public int getSaleId()          { return saleId; }
    public String getProductName()  { return productName; }
    public int getQuantitySold()    { return quantitySold; }
    public double getPriceEach()    { return priceEach; }
    public double getTotal()        { return total; }
    public String getSaleDate()     { return saleDate; }
}
