package com.hsinpingweng.library.librarymanagementsystemrestful.controller;


import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.BookRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @GetMapping("/books")
    public List<Book> retrieveAllBooks (){
        return bookRepo.findAll();
    }

    @GetMapping("/books/{id}")
    public Book retrieveBook (@PathVariable int id) throws NotFoundException {
        Optional<Book> book = bookRepo.findById(id);
        if (!book.isPresent())
            throw new NotFoundException("Book id " + id + " is not existed.");

        return book.get();
    }


    @PostMapping("/books")
    public ResponseEntity<Object> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookRepo.save(book);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).build();
    }



    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @Valid @RequestBody Book book) throws NotFoundException {
        Optional<Book> bookOpt = bookRepo.findById(id);
        if (!bookOpt.isPresent())
            throw new NotFoundException("Book id " + id + " is not existed.");

        book.setId(id);
        Book savedBook = bookRepo.save(book);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) throws NotFoundException {

        Optional<Book> book = bookRepo.findById(id);
        if (!book.isPresent())
            throw new NotFoundException("Book id " + id + " is not existed.");

        //TODO - handle constraint violation exception
        bookRepo.deleteById(id);
    }

}
