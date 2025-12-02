package com.app.ecom.dto;

import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemRequest {

    //Only two things are taken from the user
    private Long productId;
    private Integer quantity;


}
