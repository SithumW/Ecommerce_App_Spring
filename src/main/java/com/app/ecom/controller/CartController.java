package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.Product;
import com.app.ecom.service.cartService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor//inject the required arguments when obj created
public class CartController {

    private final cartService cartService;




    @GetMapping
    public ResponseEntity<List<CartItemResponse>> fetchCart(@RequestHeader("X-User-ID") String UserId){
        return ResponseEntity.ok(cartService.fetchCartItems(UserId));

    }





    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          //Get userId of RequestHeader (To identify the owner of the cart)
                                          @RequestBody CartItemRequest request){
                                            //Get the product details (CartItem)

        if(cartService.addToCart(userId,request)){
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
        else {
            return ResponseEntity.badRequest().body("Product out of stock or user not found or Product not found");

        }
        }




        @DeleteMapping("/items/{productId}")
        public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String UserId, @PathVariable Long productId){

            boolean deleted = cartService.deleteItemFromCart(UserId, productId);

            return deleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }



    }

