package com.hsinpingweng.library.librarymanagementsystemrestful.repository;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByName(String name);

}
