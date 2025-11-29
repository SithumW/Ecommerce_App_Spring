package com.app.ecom;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable //To assign into SQL type
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
