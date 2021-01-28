package com.hsinpingweng.library.librarymanagementsystemrestful.controller;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Author;
import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.AuthorRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepo;


    @GetMapping("/authors")
    public List<Author> retrieveAllAuthors (){
        return authorRepo.findAll();
    }

    @GetMapping("/authors/{id}")
    public Author retrieveAuthor(@PathVariable int id) throws NotFoundException {
        Optional<Author> author = authorRepo.findById(id);
        if (!author.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        return author.get();
    }


    @GetMapping("/authors/{id}/books")
    public Set<Book> retrieveBooksByAuthor(@PathVariable int id) throws NotFoundException {
        Optional<Author> author = authorRepo.findById(id);
        if (!author.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        return author.get().getBooks();
    }


    @PostMapping("/authors")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = authorRepo.save(author);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                .buildAndExpand(savedAuthor.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("/authors/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @Valid @RequestBody Author author) throws NotFoundException {
        Optional<Author> authorOpt = authorRepo.findById(id);
        if (!authorOpt.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        author.setId(id);
        Author savedAuthor = authorRepo.save(author);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAuthor.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable int id) throws NotFoundException {

        Optional<Author> author = authorRepo.findById(id);
        if (!author.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        //TODO - handle constraint violation exception
        authorRepo.deleteById(id);
    }

}
