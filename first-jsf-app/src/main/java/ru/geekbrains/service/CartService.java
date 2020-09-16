package ru.geekbrains.service;

import ru.geekbrains.persist.CartProduct;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CartService {

    void add(ProductRepr productRepr);

    List<CartProduct> getAllProducts();

    void removeProduct(CartProduct cartProduct);


}
