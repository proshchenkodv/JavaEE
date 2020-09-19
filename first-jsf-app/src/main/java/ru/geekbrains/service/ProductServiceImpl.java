package ru.geekbrains.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.rest.ProductServiceRs;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@WebService(endpointInterface = "ru.geekbrains.service.ProductServiceWs", serviceName = "ProductService")
public class ProductServiceImpl implements ProductService,  ProductServiceWs, ProductServiceRs {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @EJB
    private ProductRepository productRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(ProductRepr productRepr) {
        Category category = categoryRepository.findById(productRepr.getCategoryId())
                .orElse(null);
        Product product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getDescription(),
                productRepr.getPrice(),
                category);
        productRepository.insert(product);
    }



    @TransactionAttribute
    @Override
    public void update(ProductRepr productRepr) {
        Category category = categoryRepository.findById(productRepr.getCategoryId())
                .orElse(null);
        Product product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getDescription(),
                productRepr.getPrice(),
                category);
        productRepository.update(product);
    }

    @Override
    public ProductRepr findByIdRest(Long id) {
        return findByIdWs(id);
    }

    @TransactionAttribute
    @Override
    public void delete(long id) {
        productRepository.delete(id);
    }

    @Override
    public Optional<ProductRepr> findById(long id) {
        return productRepository.findById(id)
                .map(ProductRepr::new);
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public void addProd(Product product) {
        productRepository.insert(product);
    }

    @TransactionAttribute
    @Override
    public void deleteProd(long id) {
        delete(id);
    }

    @Override
    public ProductRepr findByIdWs(long id) {
        return findById(id).get();
    }

    @Override
    public ProductRepr findByName(String name) {
        return productRepository.findByName(name).map(ProductRepr::new).get();
    }

    @Override
    public List<ProductRepr> findByCategoryId(long id) {
        return productRepository.findByCategoryId(id).stream().map(ProductRepr::new).collect(Collectors.toList());
    }

//    @Override
//    public Future<ProductRepr> findByIdAsync(long id) {
//        return new AsyncResult<>(findById(id).get());
//    }


}
