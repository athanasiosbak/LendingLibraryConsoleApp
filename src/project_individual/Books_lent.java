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
public class Books_lent {
    private int id;
    private int user_send_id;
    private int user_receive_id;
    private int book_id;
    private String date;
    private String date_returned;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_send_id() {
        return user_send_id;
    }

    public void setUser_send_id(int user_send_id) {
        this.user_send_id = user_send_id;
    }

    public int getUser_receive_id() {
        return user_receive_id;
    }

    public void setUser_receive_id(int user_receive_id) {
        this.user_receive_id = user_receive_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_returned() {
        return date_returned;
    }

    public void setDate_returned(String date_returned) {
        this.date_returned = date_returned;
    }
}
