package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @EJB
    private CategoryRepository categoryRepository;

    public ProductRepository() {
    }

    public void insert(Product product) {
        logger.info("Inserting new product");
        em.persist(product);
    }

    public void update(Product product) {
        em.merge(product);
    }

    public void delete(long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }

    public Optional<Product> findById(long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }



    public List<Product> findAll() {
        return em.createQuery("from Product", Product.class)
                .getResultList();
    }

    public Optional<Product> findByName(String name) {
        Product product = em.createQuery("from Product p where p.name LIKE ?1", Product.class).setParameter(1, name).getSingleResult();
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public List<Product> findByCategoryId(long id) {
        return em.createQuery("from Product p where p.category.id = ?1", Product.class).setParameter(1, id).getResultList();

    }

}
