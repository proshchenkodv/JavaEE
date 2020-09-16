package ru.geekbrains.persist;

import ru.geekbrains.service.ProductRepr;

import java.util.Objects;

public class CartProduct {
    private Integer quantity;
    private ProductRepr productRepr;

    public CartProduct(ProductRepr productRepr){
        this.productRepr=productRepr;
        this.quantity=1;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductRepr getProductRepr() {
        return productRepr;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public boolean delQuantity(Integer quantity){
        this.quantity=this.quantity-quantity;
        if((this.quantity)>0) return true;
        else return false;
    }

    public boolean existInCart(ProductRepr productRepr){
        return this.productRepr.getId()==productRepr.getId()? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return existInCart(that.productRepr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productRepr);
    }


}