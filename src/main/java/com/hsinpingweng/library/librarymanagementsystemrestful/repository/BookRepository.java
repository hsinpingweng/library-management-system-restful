package com.hsinpingweng.library.librarymanagementsystemrestful.repository;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

}
