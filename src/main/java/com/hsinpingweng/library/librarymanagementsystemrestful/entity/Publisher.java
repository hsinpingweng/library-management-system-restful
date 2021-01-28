package com.hsinpingweng.library.librarymanagementsystemrestful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, nullable=false)
    @Size(min=2, message="Name should have at least 2 characters")
    private String name;

    @OneToMany(mappedBy="category")
    @JsonIgnore
    private Set<Book> books;

    protected Publisher() { }

    public Publisher(@Size(min = 2, message = "Name should have at least 2 characters") String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
