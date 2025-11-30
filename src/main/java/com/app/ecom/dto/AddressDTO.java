package com.app.ecom.dto;

import lombok.Data;

@Data
public class AddressDTO {
    //private String id; skip the id
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
