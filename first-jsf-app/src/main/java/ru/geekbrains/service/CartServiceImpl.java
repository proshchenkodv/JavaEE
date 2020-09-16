package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.CartProduct;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Stateful
@StatefulTimeout(value = 10, unit= TimeUnit.HOURS)
public class CartServiceImpl implements CartService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);




    private List<CartProduct> cartProductList;

    public CartServiceImpl(){
        cartProductList=new ArrayList<>();
    }

    @Override
    public void add(ProductRepr productRepr) {
        if(productRepr!=null) {
            CartProduct newCartProduct=new CartProduct(productRepr);
            if(cartProductList.contains(newCartProduct)) cartProductList.get(cartProductList.indexOf(newCartProduct)).addQuantity(1);
            else cartProductList.add(newCartProduct);
        }
    }

    @Override
    public List<CartProduct> getAllProducts() {
        return cartProductList;
    }
    @Override
    public void removeProduct(CartProduct cartProduct){

        if(cartProductList.contains(cartProduct)){
            int index=cartProductList.indexOf(cartProduct);
            if(!cartProductList.get(index).delQuantity(1)) cartProductList.remove(index);
        }


    }

    @Remove
    public void remove(){
        cartProductList=null;
    }


}
