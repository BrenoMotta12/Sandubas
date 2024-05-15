package com.example.sandubas.services.remote;

import com.example.sandubas.model.Product;
import com.example.sandubas.services.remote.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public List<Product> findAll() throws IOException, InterruptedException {

        return Api.getAllProducts();
    }

    public void saveOrUpdate(Product product) {
        if (product.getId() == null) {
            Api.insert(product);
        } else {
            Api.update(product);
        }
    }

    public void remove (Product product) {
        Api.delete(product.getId());
    }
}
