/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

/**
 *
 * @author dusht
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:inventory.db";
    
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException{
     if (connection == null || connection.isClosed()){
         connection = DriverManager.getConnection(DB_URL);
     }
     return connection;
    }
    
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
        
            // the line of codes of them databases but dont are just the starting of shit and hsti
         
            
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS users (
                        id       INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL,
                        role     TEXT NOT NULL
                        )
                        """);
            
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS products (
                        product_id TEXT PRIMARY KEY,
                        name       TEXT NOT NULL, 
                        category  TEXT NOT NULL,
                        quantity   INTEGER NOT NULL DEFAULT 0,
                        price      REAL NOT NULL DEFAULT 0.0          
                        )        
                        """);
            
            stmt.execute("""
                         CREATE TABLE IF NOT EXISTS sales (
                         sale_id        INTEGER PRIMARY KEY AUTOINCREMENT,
                         product_id     TEXT NOT NULL,
                         product_name   TEXT NOT NULL,
                         quantity_sold  INTEGER NOT NULL,
                         price_each     REAL NOT NULL,
                         total          REAL NOT NULL,
                         sale_date      TEXT NOT NULL,
                         FOREIGN KEY (product_id) REFERENCES products(product_id)
                         )
                         """);
            
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if(rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO users (username, password, role) VALUES ('admin', '1234', 'admin')");
                stmt.execute("INSERT INTO users (username, password, role) VALUES ('manager', '1234', 'manager')");
            }
            
            System.out.println("Database Succefully Initialized!");
        }
        catch (SQLException e) {
            System.err.println("Error Initializing Database : " + e.getMessage());
            }
                  
    }
    
    public static String login(String username, String password) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("role");
        }
        } catch (SQLException e){
            System.err.println("Login Error! " + e.getMessage());
            }
        return null;
    }
    
    public static List<Product> getAllProducts() {
        List<Product> list =  new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY name";
        
        try(Connection conn = getConnection(); 
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getString("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error: " + e.getMessage());
        
        }
        return list;
    }
    
    public static List<Product> searchProduct(String keyword) {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE name LIKE ? OR  product_id LIKE ? OR category LIKE ?";
    try (Connection conn = getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)){
            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Product(
                        rs.getString("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }             
    } catch (SQLException e) {
        System.err.println("Error finding products " + e.getMessage());
        }
    return list;
    }
    
    public static boolean addProduct(Product p) {
    String sql = "INSERT INTO products (product_id,  name, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
    try(Connection conn = getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, p.getProductID());
        ps.setString(2, p.getName());
        ps.setString(3, p.getCategory());
        ps.setInt(4, p.getQuantity());
        ps.setDouble(5, p.getPrice());
        ps.executeUpdate();
        return true;
    }
    
    catch (SQLException e) {
            System.err.println("Errror Adding Product" + e.getMessage());
        }
    return false;
    }
    
    public static boolean updateProduct(Product p) {
    String sql = "UPDATE products SET name=?, category=?, quantity=?, price=? WHERE product_id=?";
    try (Connection conn = getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, p.getName());
        ps.setString(2, p.getCategory());
        ps.setInt(3, p.getQuantity());
        ps.setDouble(4, p.getPrice());
        ps.setString(5, p.getProductID());
        ps.executeUpdate();
        
        return true;
        
    } catch (SQLException e) {
        System.err.println("Erorr updatign product! " + e.getMessage());
        return false;
        }
    }

    public static boolean deleteProduct(String productId) {
    String sql = "DELETE FROM products WHERE product_id = ?";
    
    try(Connection conn = getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, productId);
        ps.executeUpdate();
        return true;
    } catch (SQLException e){
        System.err.println("Error deleting Product!" + e.getMessage());
        return false;
    }
    
    }
    
    public static boolean productIdExists(String productId){
        String sql = "SELECT 1 FROM products WHERE product_id = ?";
        try(Connection conn = getConnection(); 
                PreparedStatement prepareyaass  = conn.prepareStatement(sql)){
        
            prepareyaass.setString(1, productId);
            
            ResultSet ares = prepareyaass.executeQuery();
            return ares.next();
        }catch (SQLException e) {
            return false;
        }
    
    
    }
    
    public static boolean recordSale(String productId, int quantitySold){
        try (Connection conn = getConnection()) {
            
            conn.setAutoCommit(false);
            // this part is basically to take the info of that product
            PreparedStatement ps = conn.prepareStatement(
            "SELECT name, quantity, price FROM products WHERE product_id = ?");
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) return false;
            
            String productName = rs.getString("name");
            int currentQuantity = rs.getInt("quantity");
            double price = rs.getDouble("price");
            
            if (currentQuantity < quantitySold) {
                conn.rollback();
                return false;
            }
            
            // now this part, lets try to deduct the stock
            
            PreparedStatement deduct = conn.prepareStatement(
                    "UPDATE products SET quantity = quantity - ? WHERE product_id = ?");
            deduct.setInt(1, quantitySold);
            deduct.setString(2, productId);
            deduct.executeUpdate();
            
            // now this part will insert into sale record
            
            String today = java.time.LocalDate.now().toString();
            PreparedStatement insertingsale = conn.prepareStatement(
                    "INSERT INTO sales (product_id, product_name, quantity_sold, price_each, total, sale_date) VALUES (?,?,?,?,?,?)");
            insertingsale.setString(1, productId);
            insertingsale.setString(2, productName);
            insertingsale.setInt(3, quantitySold);
            insertingsale.setDouble(4, price);
            insertingsale.setDouble(5, price * quantitySold);
            insertingsale.setString(6, today);
            insertingsale.executeUpdate();
            
            conn.commit();
            return true;
        } catch (SQLException e){
            System.err.println("Error recording sale: " + e.getMessage());
            return false;
        } 
    }
    
    public static List<Sale> getAllSales(){
        List<Sale> list = new ArrayList<>();
        String sql = "SELECT * FROM sales ORDER BY sale_id DESC";
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            
            while(rs.next()) {
                list.add(new Sale(
                        rs.getInt("sale_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity_sold"),
                        rs.getDouble("price_each"),
                        rs.getDouble("total"),
                        rs.getString("sale_date")
                
                )); 
            }
        
        }
        catch (SQLException e) {
            System.err.println("Error taking all sales " + e.getMessage());
        }
    return list;
    
    }
    
    public static List<Sale> searchSales(String keyword) {
        List<Sale> list = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE product_name LIKE ? OR CAST (sale_id AS TEXT) LIKE ? ORDER BY sale_id DESC";
        try(Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new Sale(
                    rs.getInt("sale_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity_sold"),
                    rs.getDouble("price_each"),
                    rs.getDouble("total"),
                    rs.getString("sale_date")                        
                
                ));
            }
        }
        catch (SQLException e){
            System.err.println("Error finding Sales: " + e.getMessage());
        }
        return list;
    }
    
    public static int getTotalProductCount() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products")) {
            if (rs.next()) 
                return rs.getInt(1);
        }catch (SQLException e){}
        return 0;
    }
    
    public static int getLowStock() {
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products WHERE quantity > 0 AND quantity <= 10 ")){
            if (rs.next())
                return rs.getInt(1);
        }catch (SQLException e) {}
        return 0;
    }
    
    public static int getOutofStock(){
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products WHERE quantity = 0")){
            if (rs.next()) 
                return rs.getInt(1);
        
        }catch (SQLException e) {}
        return 0;
    
    }
    
    public static double getTotalSaleAmount(){
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUM(total) FROM sales")) {
            
            if (rs.next()) 
                return rs.getDouble(1);
        } catch (SQLException e) {}
        return 0.0;
        
    }
    
    public static int getTransactionTodayCount(){
        String today = java.time.LocalDate.now().toString();
        String sql = "SELECT COUNT(*) FROM sales WHERE sale_date =?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, today);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
            
        }catch (SQLException e) {}
        return 0;
    //
    }
    
    
    
    
}
