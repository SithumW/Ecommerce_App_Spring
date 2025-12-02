package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class productService {

    private final ProductRepository productRepository; //inject productRepository


    public ProductResponse CreateProduct (ProductRequest productRequest){
        Product product = new Product();

        try{
           updateProductFromRequest(product,productRequest);
           productRepository.save(product);
           return mapToProductResponse(product);
       } catch (Exception e) {
          log.error("e: ", e);
       }
        return mapToProductResponse(product);


    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest updatedProducRequest){

       return  productRepository.findById(id)
                .map(existingProduct-> {updateProductFromRequest(existingProduct,updatedProducRequest);
                    Product savedProduct = productRepository.save(existingProduct); //save the updated to the Product obj
                    return mapToProductResponse(savedProduct); //return response

                });



    }

    public List<ProductResponse> fetchAllProducts(){

        return productRepository.findByActiveTrue().stream()
               //fetch the active ones only (Repository method is used)//
                //findByActiveTrue is not a default JPA method.

                .map(this::mapToProductResponse
                )
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(long id){
        return productRepository.findById(id).map(this::mapToProductResponse);
    }


    public boolean deleteProduct(long id){
        //get the product, set Active to false and save again
        return productRepository.findById(id)
                .map(product-> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
/*
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product Not Found!"));

        product.setActive(false);
        productRepository.save(product);*/
    }


    public List<ProductResponse> searchProduct(String keyword){
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }


    private void updateProductFromRequest(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());

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
}
