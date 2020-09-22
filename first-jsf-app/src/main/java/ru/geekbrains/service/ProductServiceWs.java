package ru.geekbrains.service;

import ru.geekbrains.persist.Product;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ProductServiceWs {

    @WebMethod
    List<ProductRepr> findAll();

    @WebMethod
    void addProd(Product product);

    @WebMethod
    void deleteProd(long id);

    @WebMethod
    ProductRepr findByIdWs(long id);

    @WebMethod
    ProductRepr findByName(String name);

    @WebMethod
    List<ProductRepr> findByCategoryId(long id);

}
