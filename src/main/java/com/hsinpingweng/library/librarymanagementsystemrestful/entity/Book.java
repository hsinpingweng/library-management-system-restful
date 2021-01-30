package com.hsinpingweng.library.librarymanagementsystemrestful.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min=10, max=13, message="Name should have between 10 to 13 characters")
    private String isbn;

    @Size(min=1, max=100, message="Name should have 1 to 100 characters")
    private String name;


    @Size(max=1000, message="Description should have at most 1000 characters")
    private String description;


    @NotNull(message = "publishedDate may not be null (Format: dd-MM-yyyy)")
    @Column(name = "published_date")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name="author_id")
    @NotNull(message = "Author may not be null")
    private Author author;

    @ManyToOne
    @JoinColumn(name="category_id")
    @NotNull(message = "Category may not be null")
    private Category category;

    @ManyToOne
    @JoinColumn(name="publisher_id")
    @NotNull(message = "Publisher may not be null")
    private Publisher publisher;

    protected Book() { }

    public Book(String isbn,
                String name,
                String description,
                Date publishedDate) {

        this.isbn = isbn;
        this.name = name;
        this.description = description;
        this.publishedDate = publishedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
