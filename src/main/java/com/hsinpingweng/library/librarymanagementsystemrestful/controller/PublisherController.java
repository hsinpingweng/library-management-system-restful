package com.hsinpingweng.library.librarymanagementsystemrestful.controller;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Publisher;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.PublisherRepository;
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
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepo;

    @GetMapping("/publishers")
    public List<Publisher> retrieveAllPublishers (){
        return publisherRepo.findAll();
    }


    @GetMapping("/publishers/{id}")
    public Publisher retrievePublisher (@PathVariable int id) throws NotFoundException {
        Optional<Publisher> publisher = publisherRepo.findById(id);
        if (!publisher.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        return publisher.get();
    }


    @GetMapping("/publishers/{id}/books")
    public Set<Book> retrieveBooksByPublisher (@PathVariable int id) throws NotFoundException {
        Optional<Publisher> publisher = publisherRepo.findById(id);
        if (!publisher.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        return publisher.get().getBooks();
    }


    @PutMapping("/publishers/{id}")
    public ResponseEntity<Object> updatePublisher(@PathVariable int id, @Valid @RequestBody Publisher publisher) throws NotFoundException {
        Optional<Publisher> publisherOpt = publisherRepo.findById(id);
        if (!publisherOpt.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        publisher.setId(id);
        Publisher savedPublisher = publisherRepo.save(publisher);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPublisher.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("/publishers")
    public ResponseEntity<Object> createPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher savedPublisher = publisherRepo.save(publisher);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPublisher.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/publishers/{id}")
    public void deletePublisher(@PathVariable int id) throws NotFoundException {

        Optional<Publisher> publisherOpt = publisherRepo.findById(id);
        if (!publisherOpt.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        //TODO - handle constraint violation exception
        publisherRepo.deleteById(id);
    }
}
