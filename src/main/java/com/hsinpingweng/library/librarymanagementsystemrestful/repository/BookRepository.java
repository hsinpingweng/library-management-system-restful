package com.hsinpingweng.library.librarymanagementsystemrestful.repository;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByIsbn(String isbn);
}
