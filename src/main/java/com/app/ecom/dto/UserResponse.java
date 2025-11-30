package com.app.ecom.dto;

import com.app.ecom.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id; // send as a String, not Long
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;

}
