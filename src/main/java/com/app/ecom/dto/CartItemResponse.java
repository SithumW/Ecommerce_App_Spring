package com.app.ecom.dto;

import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private long id;
    private ProductResponse productResponse;
    private Integer quantity;
    private BigDecimal price;


}
