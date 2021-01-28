package com.hsinpingweng.library.librarymanagementsystemrestful.service;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;

import java.util.Date;
import java.util.List;

public  interface BookService {

    List<Book> findAll(Integer id, String isbn, String name,
                       Date publishedDate, Integer authorId,
                       Integer categoryId, Integer publisher);

}

