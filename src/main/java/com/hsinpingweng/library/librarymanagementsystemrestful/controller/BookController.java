package com.hsinpingweng.library.librarymanagementsystemrestful.controller;


import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.BookRepository;
import com.hsinpingweng.library.librarymanagementsystemrestful.service.BookService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    public List<Book> retrieveBooks (@RequestParam(value = "id", required = false)Integer id,
                                     @RequestParam(value = "isbn", required = false)String isbn,
                                     @RequestParam(value = "name", required = false)String name,
                                     @RequestParam(value = "publishedDate", required = false)Date publishedDate,
                                     @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                     @RequestParam(value = "authorId", required = false) Integer authorId,
                                     @RequestParam(value = "publisherId", required = false) Integer publisherId){
        return bookService.findAll(id, isbn, name, publishedDate, categoryId, authorId, publisherId);
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
