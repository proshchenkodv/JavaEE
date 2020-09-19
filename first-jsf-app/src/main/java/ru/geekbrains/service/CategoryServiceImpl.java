package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.rest.CategoryServiceRs;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
public class CategoryServiceImpl implements CategoryServiceRs {

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(),categoryRepr.getName());
        categoryRepository.insert(category);
    }

    @TransactionAttribute
    @Override
    public void delete(long id) {
    categoryRepository.delete(id);
    }

    @TransactionAttribute
    @Override
    public void update(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(),categoryRepr.getName());
        categoryRepository.update(category);
    }

    @Override
    public CategoryRepr findByIdRest(Long id) {
        return categoryRepository.findById(id).map(CategoryRepr::new).get();
    }

    @Override
    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryRepr::new)
                .collect(Collectors
                        .toList());
    }
}
