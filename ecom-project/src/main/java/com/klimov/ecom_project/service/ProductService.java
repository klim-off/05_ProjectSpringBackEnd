package com.klimov.ecom_project.service;

import com.klimov.ecom_project.model.Product;
import com.klimov.ecom_project.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public  List<Product> getAllProducts() {
    return productRepo.findAll();
    }
}
