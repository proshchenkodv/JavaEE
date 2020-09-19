package ru.geekbrains.controller;

import ru.geekbrains.persist.CartProduct;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductRepr;

import java.io.Serializable;
import java.util.List;

//@SessionScoped
//@Named
public class CartController implements Serializable {

//    @EJB
    private CartService cartService;

    public List<CartProduct> getAllProducts() {
        return cartService.getAllProducts();
    }

    public void add(ProductRepr productRepr) {
        cartService.add(productRepr);
    }

    public void deleteProduct(CartProduct cartProduct) {
        cartService.removeProduct(cartProduct);
    }


}
