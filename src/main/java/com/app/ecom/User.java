package com.app.ecom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data //Getters and Setters (Need to access the data)
@Entity (name = "user_table")   // (name = "user_table") // Mark as a JPA entity (User keyword is reserved in SQL)
//table name -> user_table, if not mentioned, the class name will be table name
@NoArgsConstructor
//@AllArgsConstructor // will not need for now
public class User {

    @Id //Primary key = id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Will generated a unique value automatically for id field
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;
    private UserRole role = UserRole.CUSTOMER; //enum (Default = CUSTOMER)


    /*
    //We need to have a default constructor
    //JPA need to create objects without any parameters when data retrieves, so we need a default no argument constructor.
    //We can have many constructors with arguments, but this default constructor is must to work with JPA
    public User (){
    }


    public User(Long id, String firstName, String lastName, String phone, String email, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;

}
*/



}
