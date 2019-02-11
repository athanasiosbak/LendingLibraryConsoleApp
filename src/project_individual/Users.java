/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_individual;


/**
 *
 * @author Yo
 */
public class Users {
    private int id;
    private int role_id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String roledescription;

    public int getId() {
        return id;
    }

    public int getRole_id() {
        return role_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String getRoledescription() {
        return roledescription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public void setFirstname(String Firstname) {
        this.firstname = Firstname;
    }

    public void setLastname(String Lastname) {
        this.lastname = Lastname;
    }

    public void setRoledescription(String roledescription) {
        this.roledescription = roledescription;
    }
    public Users(){
        
    }
    
     
}
