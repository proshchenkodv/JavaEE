package ru.geekbrains.Utilits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {

    private long id;
    private HashMap<Product, Integer> productMap;

    public Cart(long id) {
        this.id = id;
        productMap=new HashMap<>();
    }

    public void putInCart(Product product, Integer quantity){
        productMap.put(product,productMap.get(product)+quantity);
    }

}
