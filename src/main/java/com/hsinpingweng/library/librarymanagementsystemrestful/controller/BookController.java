package com.hsinpingweng.library.librarymanagementsystemrestful.controller;


import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Author;
import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Category;
import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Publisher;
import com.hsinpingweng.library.librarymanagementsystemrestful.exception.CustomNotFoundException;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.AuthorRepository;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.BookRepository;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.CategoryRepository;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.PublisherRepository;
import com.hsinpingweng.library.librarymanagementsystemrestful.service.BookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private PublisherRepository publisherRepo;


    @ApiOperation(value="Retrieve Books by Query")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "Book id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType="query", name = "isbn", value = "ISBN", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(paramType="query", name = "name", value = "Book name", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(paramType="query", name = "published-date", value = "Published date", required = false, dataTypeClass = Date.class),
            @ApiImplicitParam(paramType="query", name = "category-id", value = "Category id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType="query", name = "author-id", value = "Author id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType="query", name = "publisher-id", value = "Publisher id", required = false, dataTypeClass = Integer.class)
    })
    @GetMapping("/books")
    public List<Book> retrieveBooks (@RequestParam(value = "id", required = false)Integer id,
                                     @RequestParam(value = "isbn", required = false)String isbn,
                                     @RequestParam(value = "name", required = false)String name,
                                     @RequestParam(value = "published-date", required = false)Date publishedDate,
                                     @RequestParam(value = "category-id", required = false) Integer categoryId,
                                     @RequestParam(value = "author-id", required = false) Integer authorId,
                                     @RequestParam(value = "publisher-id", required = false) Integer publisherId) {

        return bookService.findAll(id, isbn, name, publishedDate, categoryId, authorId, publisherId);
    }


    @ApiOperation(value="Create a book")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully create a book")
    })
    @PostMapping("/books")
    public ResponseEntity<Object> createBook(@ApiParam(name="Book to create", value="Json format", required=true) @Valid @RequestBody  Book book) throws CustomNotFoundException {

        Integer authorId = book.getAuthor().getId();
        Optional<Author> author = authorRepo.findById(authorId);
        if (!author.isPresent())
            throw new CustomNotFoundException("Author id " + authorId + " is not existed.");

        Integer categoryId = book.getCategory().getId();
        Optional<Category> category = categoryRepo.findById(categoryId);
        if (!category.isPresent())
            throw new CustomNotFoundException("Category id " + categoryId + " is not existed.");

        Integer publisherId = book.getPublisher().getId();
        Optional<Publisher> publisher = publisherRepo.findById(publisherId);
        if (!publisher.isPresent())
            throw new CustomNotFoundException("Publisher id " + publisherId + " is not existed.");

        Book savedBook = bookRepo.save(book);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Update book")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully update a book information")
    })
    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(@ApiParam("book id") @PathVariable int id,
                                             @Valid @RequestBody Book book) throws CustomNotFoundException {
        Optional<Book> bookOpt = bookRepo.findById(id);
        if (!bookOpt.isPresent())
            throw new CustomNotFoundException("Book id " + id + " is not existed.");

        Integer authorId = book.getAuthor().getId();
        Optional<Author> author = authorRepo.findById(authorId);
        if (!author.isPresent())
            throw new CustomNotFoundException("Author id " + authorId + " is not existed.");

        Integer categoryId = book.getCategory().getId();
        Optional<Category> category = categoryRepo.findById(categoryId);
        if (!category.isPresent())
            throw new CustomNotFoundException("Category id " + categoryId + " is not existed.");

        Integer publisherId = book.getPublisher().getId();
        Optional<Publisher> publisher = publisherRepo.findById(publisherId);
        if (!publisher.isPresent())
            throw new CustomNotFoundException("Publisher id " + publisherId + " is not existed.");

        book.setId(id);
        Book savedBook = bookRepo.save(book);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Delete a book")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully delete a book by id")
    })
    @DeleteMapping("/books/{id}")
    public void deleteBook(@ApiParam("book id") @PathVariable int id) throws CustomNotFoundException {

        Optional<Book> book = bookRepo.findById(id);
        if (!book.isPresent())
            throw new CustomNotFoundException("Book id " + id + " is not existed.");

        bookRepo.deleteById(id);
    }

}
