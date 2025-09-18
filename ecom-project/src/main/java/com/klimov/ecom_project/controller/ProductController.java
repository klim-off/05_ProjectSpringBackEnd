package com.klimov.ecom_project.controller;

import com.klimov.ecom_project.model.Product;
import com.klimov.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    //public  Product getProduct (@PathVariable int id) {
    //     return service.getProductById(id);
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {
            Product newProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            //  throw new RuntimeException(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();
        return ResponseEntity.ok().
                contentType(MediaType.valueOf(product.getImageType())).
                body(imageFile);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile
    ) {

        try {
            Product upProduct = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to updated", HttpStatus.BAD_REQUEST);

        }

        if (product != null)
            return new ResponseEntity<>("updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct (@PathVariable int id) {

        Product product = service.getProductById(id);
        if (product!= null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts (@RequestParam String keyword){
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
