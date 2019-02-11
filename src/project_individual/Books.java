/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_individual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yo
 */
public class Books {
    private int id;
    private int userId;
    private Users user;
    private String name;
    private String author;
    private String rating;
    private String category;
    private int status;
    
    
    public int getId() {
        return id;
    }
    
    public Users getUser() {
        return user;
    }


    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getRating() {
        return rating;
    }


    public String getCategory() {
        return category;
    }

    public int getStatus() {
        return status;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public Books(){
        
    }
  
    public Books(int id){
        Database.connect();
        try{
            PreparedStatement ps = Database.getConn().prepareStatement("SELECT * FROM books WHERE id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                this.id = rs.getInt("id");
                this.userId = rs.getInt("users_id");
                this.name = rs.getString("name");
                this.author = rs.getString("author");
                this.category = rs.getString("category");
                this.rating = rs.getString("rating");
                this.status = rs.getInt("status");
            }
            else{
                this.id = -1 ;
                this.name = "" ;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        } 
    }

}
