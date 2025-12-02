package com.app.ecom.repository;

import com.app.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Remember, ProductRepository is an interface
    //So methods cannot have a body
    //But JPA is smart enough to know that you are filtering based on the Active == True
    //findBy -> Selection querry
    //ActiveTrue -> where active == true

    List<Product> findByActiveTrue();


    //We use searchProducts method to search for specific products using keyword
    //@Query annotation is used to write a custom SQL query
    @Query("SELECT p FROM products p WHERE p.active = true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword")String keyword);
    //Only active products are picked up
    //Product should be in stock
    //Lower() to convert to lower case
    //LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%') ->  If product name contains the given keyword
    //%keyword% is used in pattern matching (keyword can be anywhere in the name)

    //@Param(keyword) will convert keyword that come as argument to query parameter to pass to the query.
}
