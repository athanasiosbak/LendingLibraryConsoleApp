/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_individual;

import static java.lang.System.exit;
import java.sql.SQLException;




/**
 *
 * @author Yo
 */
public class Menu {
    
    public static void start() throws SQLException {
        boolean t = true;
        while(t){
        String [] choice1 = {"1. Login","2. Register","3. Exit"};
        System.out.println("------------------------------");
        for(int x1=0; x1<choice1.length; x1++){
            System.out.println(choice1[x1]);
        }
        System.out.println("------------------------------");
        System.out.println("");
        System.out.println("What do you want to do:");
        int temp = Database.checkIfInt();
        switch(temp){
            case 1:
                int x = Database.login1();
                while(x == 0){
                    x = Database.login1();
                }
                if (Database.getCurrentRole()==0){
                    System.out.println("Super Admin logged in !!!");
                    superAdminMenu();
                }
                else if (Database.getCurrentRole()==1){
                    menuUserA();
                }
                else if(Database.getCurrentRole()==2){
                    menuUserB();
                }
                else{
                    menuUserC();
                }
                break;
            case 2:
                insertNewUser();
                break;
            case 3:
               break;
            default:
                System.out.println("Wrong input");
                System.out.println("");
                break;
       }
       }
    }
   
    public static void insertNewUser(){
        System.out.println("Give me your role id:");
        int role_id = Database.checkIfInt();
        // scan.nextLine();
        System.out.println("Give me your username");
        String username = Database.checkIfString();
        System.out.println("Give me your password");
        String password = Database.checkIfString();
        System.out.println("Give me your firstname");
        String firstname = Database.checkIfString();
        System.out.println("Give me your lastname");
        String lastname = Database.checkIfString();
        System.out.println("Give me your role description");
        String roledescription = Database.checkIfString();
        if(Database.createUser(role_id, username, password, firstname, lastname, roledescription)){
            System.out.println("Insert succesfully");
        }
        else{
            System.out.println("Insert failed");
        }
    }
    public static void superAdminMenu(){
        boolean t = true;
        while(t){
            String choice [] ={"1. Create user" ,"2. View users" , "3. Delete user" , "4. Update user" ,
            "5. Search books" , "6. Exit"};
            System.out.println("------------------------------");
            for(int x=0; x<choice.length; x++){
                System.out.println(choice[x]);
            }
            System.out.println("------------------------------");
            System.out.println("");
            System.out.println("What do you want to do:");
            int temp = Database.checkIfInt();
            switch(temp){
                case 1:
                    Database.insertNewUser();
                    break;
                case 2:
                    Database.seeUsers();
                    break;
                case 3:
                    System.out.println("------------------------------------------------------");
                    Database.seeUsers2();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the user you want to delete ");
                    int id = Database.checkIfInt();
                    boolean flag = Database.deleteUser(id);
                    if(flag){
                        System.out.println("Deleted succesfully");
                    }
                    else{
                        System.out.println("Unsuccesfull delete");
                    }
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    Database.seeUsers();
                    System.out.println("------------------------------------------------------------------------------------------------");
                    System.out.println("Which ID you want to update:");
                    int temp1 =Database.checkIfInt();
                    System.out.println("Which column do you want to update:");
                    String updchoice [] ={"1. Column (roleID)" ,"2. Column (username)" , "3. Column (password)" , "4. Column (firstname)" ,
                    "5. Column (lastname)" ,   "6. Column (roledescription)" ,"7. Return"};
                    System.out.println("------------------------------");
                    for(int x=0; x<updchoice.length; x++){
                        System.out.println(updchoice[x]);
                    }
                    System.out.println("------------------------------");
                    System.out.println("");
                    int temp2 = Database.checkIfInt();
                    Database.updateUser(temp1,temp2);
                    break;
                case 5:
                    Database.viewBooks();
                    break;
                case 6:
                    exit(0);
                    break;
                default:
                    System.out.println("Wrong input");
                    System.out.println("");
                    break;
            }
        }
        
    }
    public static void menuUserA(){
        boolean t = true;
        while(t){
            String choice [] = {"1. Search book" , "2. Add a book" , "3. Delete a book" , "4. Update a book" , "5. Rent a book"
            , "6. Return a book" , "7. View all rented books" , "8. Exit"};
            System.out.println("------------------------------");
            for(int x=0; x<choice.length; x++){
                System.out.println(choice[x]);
            }
            System.out.println("------------------------------");
            System.out.println("What do you want to do:");
            int temp = Database.checkIfInt();
            switch(temp){
                case 1:
                   searchMenu();
                   break;
                case 2:
                    Database.insertNewBook();
                    break;
                case 3:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooks();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to delete ");
                    int id = Database.checkIfInt();
                    Database.deleteBook(id);
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    Database.viewBooksCurrent();
                    System.out.println("------------------------------------------------------------------------------------------------");
                    System.out.println("Which ID you want to update:");
                    int temp1 =Database.checkIfInt();
                    System.out.println("Which column do you want to update:");
                    String updchoice [] ={"1. Column (name)" ,"2. Column (author)" , "3. Column (category)" , "4. Column (rating)" , "5. Return"};
                    System.out.println("------------------------------");
                    for(int x=0; x<updchoice.length; x++){
                        System.out.println(updchoice[x]);
                    }
                    System.out.println("------------------------------");
                    System.out.println("");
                    int temp2 = Database.checkIfInt();
                    Database.updateBook(temp1,temp2);
                    break;
                case 5:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooksAvailable();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to rent ");
                    int rentid = Database.checkIfInt();
                    Database.rentBook(rentid);
                    break;
                case 6:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooksAvailable();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to return : ");
                    int returnid = Database.checkIfInt();
                    Database.returnBook(returnid);
                    break;
                case 7:
                    System.out.println("------------------------------------------------------");
                    Database.viewRented();
                    System.out.println("------------------------------------------------------");
                    break;
                case 8:
                    exit(0);
                    break;
            }
        }
    }
    public static void menuUserB(){
        boolean t = true;
        while(t){
            String choice [] = {"1. Search book" , "2. Add a book" , "3. Delete a book" , "4. Update a book" , "5. Rent a book"
            , "6. Return a book" , "7. View all rented books" , "8. Edit rented books data" , "9. Exit"};
            System.out.println("------------------------------");
            for(int x=0; x<choice.length; x++){
                System.out.println(choice[x]);
            }
            System.out.println("------------------------------");
            System.out.println("What do you want to do:");
            int temp = Database.checkIfInt();
            switch(temp){
                case 1:
                   searchMenu();
                   break;
                case 2:
                    Database.insertNewBook();
                    break;
                case 3:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooks();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to delete ");
                    int id = Database.checkIfInt();
                    Database.deleteBook(id);
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    Database.viewBooksCurrent();
                    System.out.println("------------------------------------------------------------------------------------------------");
                    System.out.println("Which ID you want to update:");
                    int temp1 =Database.checkIfInt();
                    System.out.println("Which column do you want to update:");
                    String updchoice [] ={"1. Column (name)" ,"2. Column (author)" , "3. Column (category)" , "4. Column (rating)" , "5. Return"};
                    System.out.println("------------------------------");
                    for(int x=0; x<updchoice.length; x++){
                        System.out.println(updchoice[x]);
                    }
                    System.out.println("------------------------------");
                    System.out.println("");
                    int temp2 = Database.checkIfInt();
                    Database.updateBook(temp1,temp2);
                    break;
                case 5:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooksAvailable();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to rent ");
                    int rentid = Database.checkIfInt();
                    Database.rentBook(rentid);
                    break; 
                case 6:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooksAvailable();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to return : ");
                    int returnid = Database.checkIfInt();
                    Database.returnBook(returnid);
                    break;
                case 7:
                    System.out.println("------------------------------------------------------");
                    Database.viewRented();
                    System.out.println("------------------------------------------------------");
                    break;
                case 8:
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    Database.viewRented();
                    System.out.println("------------------------------------------------------------------------------------------------");
                    System.out.println("Which ID you want to update:");
                    int tempid =Database.checkIfInt();
                    System.out.println("Which column do you want to update:");
                    String c [] ={"1. Column (User_send_id)" ,"2. Column (user_receive_id)" , "3. Column (book_id)" , "4. Column (date)" , "5. Column (date returned)" , "6. Return"};
                    System.out.println("------------------------------");
                    for(int x=0; x<c.length; x++){
                        System.out.println(c[x]);
                    }
                    System.out.println("------------------------------");
                    System.out.println("");
                    int tempc = Database.checkIfInt();
                    Database.updateBook(tempid,tempc);
                case 9:
                    exit(0);
                    break;
            }
        }
    }
    public static void menuUserC(){
        boolean t = true;
        while(t){
            String choice [] = {"1. Search book" , "2. Add a book" , "3. Delete a book" , "4. Update a book" , "5. Rent a book"
            , "6. Return a book" ,"7. View all rented books" , "8. Edit rented books data" , "9. Delete rented books data" , "10. Exit"};
            System.out.println("------------------------------");
            for(int x=0; x<choice.length; x++){
                System.out.println(choice[x]);
            }
            System.out.println("------------------------------");
            System.out.println("What do you want to do:");
            int temp = Database.checkIfInt();
            switch(temp){
                case 1:
                   searchMenu();
                   break;
                case 2:
                    Database.insertNewBook();
                    break;
                case 3:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooks();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to delete ");
                    int id = Database.checkIfInt();
                    Database.deleteBook(id);
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    Database.viewBooksCurrent();
                    System.out.println("------------------------------------------------------------------------------------------------");
                    System.out.println("Which ID you want to update:");
                    int temp1 =Database.checkIfInt();
                    System.out.println("Which column do you want to update:");
                    String updchoice [] ={"1. Column (name)" ,"2. Column (author)" , "3. Column (category)" , "4. Column (rating)" , "5. Return"};
                    System.out.println("------------------------------");
                    for(int x=0; x<updchoice.length; x++){
                        System.out.println(updchoice[x]);
                    }
                    System.out.println("------------------------------");
                    System.out.println("");
                    int temp2 = Database.checkIfInt();
                    Database.updateBook(temp1,temp2);
                    break;
                case 5:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooksAvailable();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to rent :");
                    int rentid = Database.checkIfInt();
                    Database.rentBook(rentid);
                    break;
                case 6:
                    System.out.println("------------------------------------------------------");
                    Database.viewBooksAvailable();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the book you want to return : ");
                    int returnid = Database.checkIfInt();
                    Database.returnBook(returnid);
                    break;
                case 7:
                    System.out.println("------------------------------------------------------");
                    Database.viewRented();
                    System.out.println("------------------------------------------------------");
                    break;
                case 8:
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    Database.viewRented();
                    System.out.println("------------------------------------------------------------------------------------------------");
                    System.out.println("Which ID you want to update:");
                    int tempid =Database.checkIfInt();
                    System.out.println("Which column do you want to update:");
                    String c [] ={"1. Column (User_send_id)" ,"2. Column (user_receive_id)" , "3. Column (book_id)" , "4. Column (date)" , "5. Column (date returned)" , "6. Return"};
                    System.out.println("------------------------------");
                    for(int x=0; x<c.length; x++){
                        System.out.println(c[x]);
                    }
                    System.out.println("------------------------------");
                    System.out.println("");
                    int tempc = Database.checkIfInt();
                    Database.updateBook(tempid,tempc);
                case 9:
                    System.out.println("------------------------------------------------------");
                    Database.viewRented();
                    System.out.println("------------------------------------------------------");
                    System.out.println("Type the id of the entry you want to delete :");
                    int deletelid = Database.checkIfInt();
                    Database.deleteRented(deletelid);
                    break;
                case 10:
                    exit(0);
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }
    public static void searchMenu(){
        String choice [] = {"1. See all books " , "2. See your books " ,"3. Search a book by name"
        , "4. Search a book by author" , "5. Search a book by category " , "6. Search a book by rating" , "7. Show all available books" , "8. Return"};
        System.out.println("------------------------------");
        for(int x=0; x<choice.length; x++){
            System.out.println(choice[x]);
        }
        System.out.println("------------------------------");
        System.out.println("What do you want to do:");
        int temp = Database.checkIfInt();
        switch(temp){
            case 1:
                Database.viewBooks();
                break;
            case 2:
                Database.viewBooksCurrent();
                break;
            case 3:
                System.out.println("Give me Book's name:");
                String name = Database.checkIfString();
                Database.viewBooksName(name);
                break;
            case 4:
                System.out.println("Give me Book's author:");
                String author = Database.checkIfString();
                Database.viewBooksAuthor(author);
                break;
            case 5:
                System.out.println("Give me Book's category:");
                String category = Database.checkIfString();
                Database.viewBooksCategory(category);
                break;
            case 6:
                System.out.println("Give me Book's rating:");
                String rating = Database.checkIfString();
                Database.viewBooksCategory(rating);
                break;
            case 7:
                Database.viewBooksAvailable();
                break;
            case 8:
                menuUserA();
                break;
        }
    }
}
