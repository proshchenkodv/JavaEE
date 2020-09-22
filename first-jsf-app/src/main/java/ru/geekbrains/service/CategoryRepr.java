package ru.geekbrains.service;

import ru.geekbrains.persist.Category;

public class CategoryRepr {

    private Long id;

    private String name;

    public CategoryRepr(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryRepr() {
    }

    public CategoryRepr(String name) {
        this.name = name;
    }

    public CategoryRepr(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
