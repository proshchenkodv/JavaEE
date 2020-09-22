package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class CompanyRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    public CompanyRepository() {
    }

    @Transactional
    public void insert(Company company) {
        em.persist(company);
    }

    @Transactional
    public void update(Company company) {
        em.merge(company);
    }

    @Transactional
    public void delete(long id) {
        Company company = em.find(Company.class, id);
        if (company != null) {
            em.remove(company);
        }
    }

    public Optional<Company> findById(long id) {
        Company company = em.find(Company.class, id);
        if (company != null) {
            return Optional.of(company);
        }
        return Optional.empty();
    }

    public List<Company> findAll() {
        return em.createQuery("from Company ", Company.class)
                .getResultList();
    }

    public Optional<Company> findByName(String name) {
        Company company = em.createQuery("from Company c where c.name = :name", Company.class)
                .setParameter("name", name)
                .getSingleResult();
        if (company != null) {
            return Optional.of(company);
        } else {
            return Optional.empty();
        }
    }
}
