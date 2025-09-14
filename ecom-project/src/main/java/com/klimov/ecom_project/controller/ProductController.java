package com.klimov.ecom_project.controller;

import com.klimov.ecom_project.model.Product;
import com.klimov.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

     @GetMapping("/products")
    //public List<Product> getAllProducts(){
     // return service.getAllProducts();
    public ResponseEntity<List<Product>> getAllProducts(){
       return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    //public  Product getProduct (@PathVariable int id) {
    //     return service.getProductById(id);
    public ResponseEntity<Product> getProduct (@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
