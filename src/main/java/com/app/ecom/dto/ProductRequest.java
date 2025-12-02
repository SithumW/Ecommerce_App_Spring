package com.app.ecom.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data //Getters and setters are generated
public class ProductRequest {

    //private long id; Auto Generated
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String Category;
    private String imageUrl;
  //  private boolean active; default value is given when created


}
