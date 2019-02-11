/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_individual;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yo
 */
public class Database {
    private static final String DB_URL = "localhost:3306";
    private static final String db_name = "database_individual";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/" + db_name + "?zeroDateTimeBehavior=convertToNull";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "nassos4686"; 
    private static Connection conn;

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection aConn) {
        conn = aConn;
    }
    private String username;
    private String superAdmin;
    public static int role;
    public static int idb;
    private static String currentUsername;
    private static int currentRole;
    private static int currentID;
    
    
    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String aCurrentUsername) {
        currentUsername = aCurrentUsername;
    }

    public static int getCurrentRole() {
        return currentRole;
    }

    public static void setCurrentRole(int aCurrentRole) {
        currentRole = aCurrentRole;
    }

    public static int getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(int aCurrentID) {
        currentID = aCurrentID;
    }
    
    public String getUsername() {
        return username;
    }

    public String getSuperAdmin() {
        return superAdmin;
    }
    public Database(Connection conn) {
        this.conn = conn;
    }

    public Database() {
        
    }
    
    public static boolean connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(FULL_DB_URL,DB_USER,DB_PASSWD);
            return true;
        }
        catch(Exception exc){
            System.out.println("Could not connect to database");
            return false;
        }
    }
    
    public static void close(){
        try{
            if(conn != null){
                conn.close();            }
        }
        catch(Exception exc){
               System.out.println("Couldn't close connection: "+exc);
        }
    }
    
    public static int login1() throws SQLException{
            connect();
            String uname;
            String pass;
            System.out.println("----------------------");
            System.out.println("Give username: ");
            uname = checkIfString();
            System.out.println("----------------------");
            System.out.println("Give password: ");
            pass = checkIfString();
            try{
                PreparedStatement q = conn.prepareStatement("SELECT * FROM users  WHERE username='"+uname+"' AND password='"+pass+"';");
                ResultSet result = q.executeQuery();
                if(result.next()){
                    System.out.println("");
                    System.out.println("Welcome "+uname.toUpperCase() + "  !!!");
                    System.out.println("----------------------");
                    currentID = result.getInt(1);
                    currentRole = result.getInt(2);
                    currentUsername = uname;
                    close();
                    return 1;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());   
            }
            System.out.println("Wrong input");
            return 0;
    }
    
    public static boolean createUser(int role_id,String username,String password,String firstname,String lastname,String roledescription){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (NULL,?,?,?,?,?,?)");
            ps.setInt(1,role_id);
            ps.setString(2,username);
            ps.setString(3,password);
            ps.setString(4, firstname);
            ps.setString(5, lastname);
            ps.setString(6,roledescription);
            ps.executeUpdate();
            close();
            return true;
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static void insertNewUser(){
        System.out.println("Give me your role id:");
        int role_id = Database.checkIfInt();
        System.out.println("Give me your username: ");
        String username = Database.checkIfString();
        System.out.println("Give me your password:");
        String password = Database.checkIfString();
        System.out.println("Give me your firstname:");
        String firstname = Database.checkIfString();
        System.out.println("Give me your lastname:");
        String lastname = Database.checkIfString();
        System.out.println("Give me your role description:");
        String roledescription = Database.checkIfString();
        if(createUser(role_id, username, password, firstname, lastname, roledescription)){
            System.out.println("Insert succesfully");
        }
        else{
            System.out.println("Insert failed");
        }   
    }
    
    public static boolean createBook(String name,String author,String category,String rating){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO books VALUES (NULL,?,?,?,?,?)");
            ps.setInt(1,currentID);
            ps.setString(2,name);
            ps.setString(3,author);
            ps.setString(4, category);
            ps.setString(5, rating);
            ps.setInt(6,0);
            ps.executeUpdate();
            close();
            return true;
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static void insertNewBook(){
        System.out.println("What is the name of the book ? ");
        String name = Database.checkIfString();
        System.out.println("Who is the author ?");
        String author = Database.checkIfString();
        System.out.println("Which category does it belong to ?");
        String category = Database.checkIfString();
        System.out.println("What is it's rating ?");
        String rating = Database.checkIfString();
        if(createBook(name, author, category, rating)){
            System.out.println("Insert succesfully");
        }
        else{
            System.out.println("Insert failed");
        }   
    }
    
    public static boolean rentBook(int id){
        try {
            Books b = new Books(id);
            connect();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO books_lent VALUES (NULL,?,?,?,?,NULL)");
            ps.setInt(1,b.getUserId());
            ps.setInt(2,currentID);
            ps.setInt(3,id);
            ps.setString(4, Date.now());
            ps.executeUpdate();
            updateStatus1(id);
            close();
            return true;
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static void updateStatus1(int id){
        try{
            connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE books SET status = '1' where id = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            close();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static void returnBook(int id){
        try{
            connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE books_lent SET date_returned = ? WHERE user_receive = ? AND book_id = ?");
            ps.setString(1, Date.now());
            ps.setInt(2, currentID);
            ps.setInt(3, id);
            ps.executeUpdate();
            updateStatus0(id);
            close();  
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static void updateStatus0(int id){
        try{
            connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE books SET status = '0' where id = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            close();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static void seeUsers(){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Users user = new Users();
                user.setId(rs.getInt(1));
                user.setRole_id(rs.getInt(2));
                user.setUsername(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setFirstname(rs.getString(5));
                user.setLastname(rs.getString(6));
                user.setRoledescription(rs.getString(7));
                System.out.println("ID :" + user.getId() +   "  RoleID :"  + user.getRole_id()   + "  Username :" + user.getUsername() + "  Password:" + user.getPassword() + "  Firstname :" +
                user.getFirstname() + "  Lastname :"+ user.getLastname() + "  Role Description :" + user.getRoledescription());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void seeUsers2(){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Users user = new Users();
                user.setId(rs.getInt(1));
                user.setFirstname(rs.getString(5));
                user.setLastname(rs.getString(6));
                System.out.println("ID :" + user.getId() + "  Firstname :" + user.getFirstname() + "  Lastname :"+ user.getLastname());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean deleteUser(int temp){
        try{
            connect();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, temp);
            ps.executeUpdate();
            close();
            return true;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static void deleteBook(int temp){
        try{
            connect();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id = ? AND users_id = ?");
            ps.setInt(1, temp);
            ps.setInt(2,currentID);
            int flag = ps.executeUpdate();
            if(flag == -1){
                System.out.println("Unsuccesfull update");
                System.out.println("You cannot delete a book from another user");
            }
            else{
                System.out.println("Succesfull update");
                }
            close();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }  
    }
    
    public static void updateUser(int temp1,int temp2){
        switch(temp2){
            case 1:
                System.out.println("Give user's roleID new value");
                int roleid = Database.checkIfInt();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET role_id = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setInt(2, roleid);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 2:
                System.out.println("Give user's username new value");
                String username = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET username = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, username);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 3:
                System.out.println("Give user's password new value");
                String password = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, password);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 4:
                System.out.println("Give user's firstname new value");
                String firstname = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET firstname = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, firstname);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 5:
                System.out.println("Give user's lastname new value");
                String lastname = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET lastname = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, lastname);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 6:
                System.out.println("Give user's role description new value");
                String roledescription = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET roledescription = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, roledescription);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 7:
                Menu.superAdminMenu();
                break;
        }
    }
    
    public static void updateBook(int temp1,int temp2){
        switch(temp2){
            case 1:
                System.out.println("What's the book's new name ?");
                String name = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books SET name = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, name);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 2:
                System.out.println("What's the book's new author ?");
                String author = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books SET author = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, author);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 3:
                System.out.println("What's the book's new category ?");
                String category = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books SET category = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, category);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 4:
                System.out.println("What's the book's new rating ?");
                String rating = Database.checkIfString();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books SET rating = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, rating);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 5:
                Menu.menuUserA();
                break;      
        }
    }
    
    public static void updateBookLent(int temp1,int temp2){
        switch(temp2){
            case 1:
                System.out.println("What's the new user sender id ?");
                int sid = Database.checkIfInt();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books_lent SET user_send = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setInt(2, sid);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 2:
                System.out.println("What's the new user receiver id ?");
                int rid = Database.checkIfInt();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books_lent SET user_receive = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setInt(2, rid);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 3:
                System.out.println("What's the new book's id ?");
                int bid = Database.checkIfInt();
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books_lent SET book_id = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setInt(2, bid);
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 4:
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books_lent SET date = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, Date.now());
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 5:
                connect();
                try {
                    PreparedStatement ps = conn.prepareStatement("UPDATE books_lent SET date_returned = ? WHERE id = ?");
                    ps.setInt(1,temp1);
                    ps.setString(2, Date.now());
                    int flag = ps.executeUpdate();
                    close();
                    if(flag == -1){
                        System.out.println("Unsuccesfull update");
                    }
                    else{
                        System.out.println("Succesfull update");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 6:
                Menu.menuUserA();
                break;      
        }
    }
    
    public static void viewRented(){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books_lent");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books_lent b = new Books_lent();
                b.setId(rs.getInt(1));
                b.setUser_send_id(rs.getInt(2));
                b.setUser_receive_id(rs.getInt(3));
                b.setBook_id(rs.getInt(4));
                b.setDate(rs.getString(5));
                b.setDate_returned(rs.getString(6));
                System.out.println("ID :" + b.getId() +   "  User sender ID :"  + b.getUser_send_id()   + "  User receiver ID :" + b.getUser_receive_id() + "  Book's ID :" + b.getBook_id() + "  Date :" +
                b.getDate() + "  Date returned :" + b.getDate_returned());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void deleteRented(int id){
        try{
            connect();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM books_lent WHERE id = ? ");
            ps.setInt(1, id);
            int flag = ps.executeUpdate();
            if(flag == -1){
                System.out.println("Unsuccesfull update");
            }
            else{
                System.out.println("Succesfull update");
                }
            close();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }  
    }
    
    public static void viewBooks(){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void viewBooksCurrent(){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE users_id = ?");
            ps.setInt(1, currentID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void viewBooksName(String s){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE name LIKE ?" );
            ps.setString(1, "%" + s + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void viewBooksAuthor(String s){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE author LIKE ?" );
            ps.setString(1, "%" + s + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void viewBooksCategory(String s){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE category LIKE ?" );
            ps.setString(1, "%" + s + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void viewBooksRating(String s){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE rating LIKE ?" );
            ps.setString(1, "%" + s + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void viewBooksAvailable(){
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE status = ?" );
            ps.setInt(1, 0);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setUserId(rs.getInt(2));
                book.setName(rs.getString(3));
                book.setAuthor(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setRating(rs.getString(6));
                book.setStatus(rs.getInt(7));
                System.out.println("ID :" + book.getId() +   "  User ID :"  + book.getUserId()   + "  Name :" + book.getName() + "  Author :" + book.getAuthor() + "  Category :" +
                book.getCategory() + "  Rating :" + book.getRating() + "  Status :" + book.getStatus());
            }
            rs.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
 
    
    
    
    public static int checkIfInt(){
        Scanner scan = new Scanner (System.in);
        String tempStr ;
        tempStr = scan.nextLine();
        while(!isNumeric(tempStr)){
            System.out.println("Wrong input ,give me a number :");
            tempStr = scan.nextLine();
        }
        int temp = Integer.parseInt(tempStr);
        return temp;
    }
    public static String checkIfString(){
        Scanner scan = new Scanner (System.in);
        String temp = scan.nextLine();
        while(isNumeric(temp)){
            System.out.println("Wrong input , type again :");
            temp = scan.nextLine();
        }
        return temp;
    }
   
    public static boolean isNumeric(String str){
    try{
        int d = Integer.parseInt(str);
    }
    catch(NumberFormatException nfe){
        return false;
    }
      return true;
    }
    
}