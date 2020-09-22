package ru.geekbrains.persist;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String address;

//    @OneToMany(
//            mappedBy = "company",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Category> categories;

    public Company() {
    }

    public Company(Long id, String name, String adress) {
        this.id = id;
        this.name = name;
        this.address = adress;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}


