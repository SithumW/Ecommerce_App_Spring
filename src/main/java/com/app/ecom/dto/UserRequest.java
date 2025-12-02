package com.app.ecom.dto;


import lombok.Data;

@Data
public class UserRequest {

    private String id; // send as a String, not Long
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
//    private UserRole role; Role is no needed (Auto assigns)

    private AddressDTO address;
}
