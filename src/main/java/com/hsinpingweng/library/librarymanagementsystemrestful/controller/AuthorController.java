package com.hsinpingweng.library.librarymanagementsystemrestful.controller;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Author;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.AuthorRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

}
