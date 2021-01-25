package com.hsinpingweng.library.librarymanagementsystemrestful.controller;


import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Category;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.CategoryRepository;
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
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/categories")
    public List<Category> retrieveAllCategories (){
        return categoryRepo.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category retrieveCategory (@PathVariable int id) throws NotFoundException {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        return category.get();
    }


    @PutMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @Valid @RequestBody Category category) throws NotFoundException {
        Optional<Category> categoryOpt = categoryRepo.findById(id);
        if (!categoryOpt.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        category.setId(id);
        Category savedCategory = categoryRepo.save(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryRepo.save(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable int id) throws NotFoundException {

        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        //TODO - handle constraint violation exception
        categoryRepo.deleteById(id);
    }

}
