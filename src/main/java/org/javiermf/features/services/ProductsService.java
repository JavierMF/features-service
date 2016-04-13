package org.javiermf.features.services;

import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    ProductsDAO productsDAO;


    public List<String> getAllProductNames() {
        List<String> allProducts = new ArrayList<String>();

        for (Product product : productsDAO.findAll()) {
            allProducts.add(product.getName());
        }

        return allProducts;
    }

    public Product findByName(String productName) {
        return productsDAO.findByName(productName);
    }
}
