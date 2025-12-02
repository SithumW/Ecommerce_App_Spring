package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional //To run the Delete Operations
public class cartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        //---Validation---
        //Check the product exists
        Optional<Product> productOpt = productRepository.findById((request.getProductId()));
        if(productOpt.isEmpty()){
            return false;
        }

        //save product if found
        Product product = productOpt.get();

        //Check quantity
        if(product.getStockQuantity() < request.getQuantity()){
            return false;
        }

        //Check user exists
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }

        //save user from optional to product;
        User user = userOpt.get();


        //--Check if product Already exists in user Cart or not --
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(existingCartItem != null){
            //Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity()); //quantity ++
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }
        else{
            //Create a new Cart Item
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setUser(user);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(newCartItem);

        }

        return true;
    }

    public boolean deleteItemFromCart (String userId, Long productId){

        //---Validation---
        //Check the product and user exists
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }

        return  false;


    }


    public List<CartItemResponse> fetchCartItems(String userId) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        User user = userOpt.get();


        return cartItemRepository.findByUser(user).stream()
                .map(cartItem -> {
                    CartItemResponse cartItemResponse = new CartItemResponse(); //create new for each cartItem
                    cartItemResponse.setId(cartItem.getId());
                    cartItemResponse.setQuantity(cartItem.getQuantity());
                    cartItemResponse.setPrice(cartItem.getPrice());

                    Product product = cartItem.getProduct();
                    ProductResponse productResponse = mapToProductResponse(product);
                    cartItemResponse.setProductResponse(productResponse);

                    return cartItemResponse;

                }).toList();



        /*
        Without using a CartResponse : Directly the cart will be returned.

        return userRepository.findById(Long.valueOf(userId))
        .map(cartItemRepository::findByUser)
        .orElseGet(List::of); // else return an empty list
        * */
    }


    private ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setCategory(product.getCategory());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setActive(product.getActive());

        return productResponse;
    }

    public void clearCart(String userId) { //delete cart items of particular user
        userRepository.findById(Long.valueOf(userId)).ifPresent(
                cartItemRepository::deleteByUser
                );
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findById(Long.valueOf(userId)).stream().toList();
    }
}
