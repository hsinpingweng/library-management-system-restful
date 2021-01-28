package com.hsinpingweng.library.librarymanagementsystemrestful.repository;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByIsbn(String isbn);

    Set<Book> findByCategory_Id(int categoryId);

    Set<Book> findByAuthor_Id(int authorId);

}
