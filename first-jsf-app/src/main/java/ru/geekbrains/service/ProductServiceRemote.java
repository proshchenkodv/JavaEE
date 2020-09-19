package ru.geekbrains.service;


import javax.ejb.Asynchronous;
import javax.ejb.Remote;
import java.util.List;
import java.util.concurrent.Future;

@Remote
public interface ProductServiceRemote {
    @Asynchronous
    Future<ProductRepr> findByIdAsync(long id);

    List<ProductRepr> findAll();
}
