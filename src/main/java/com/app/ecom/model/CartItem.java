package com.app.ecom.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "CartItem")
@Data
public class CartItem {

    //CartItem is a single Table that contain all the products added to all the carts by all the users


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //Relations work on both sides.
    //Many To One = One To Many
    @ManyToOne //One user can add many cart items
    @JoinColumn(name="user_id", nullable = false)
    private User user;


    @ManyToOne //One cart have many products
    @JoinColumn(name="product_id", nullable = false)
    private Product product;


    private Integer quantity;
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatetAt;

}
