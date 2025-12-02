package com.app.ecom.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductResponse {
    //These info will be presented to the user
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String Category;
    private String imageUrl;
    private Boolean active;//whether the product is available in database
}
