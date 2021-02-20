package com.hsinpingweng.library.librarymanagementsystemrestful.impl;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Book;
import com.hsinpingweng.library.librarymanagementsystemrestful.repository.BookRepository;
import com.hsinpingweng.library.librarymanagementsystemrestful.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {


    @Autowired
    private BookRepository bookRepo;


    @Override
    public List<Book> findAll(Integer id, String isbn, String name, Date publishedDate,
                              Integer categoryId, Integer authorId, Integer publisherId) {

        Specification specification = (Specification<Book>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (id != null)
                predicateList.add(cb.equal(root.get("id").as(Integer.class), id));

            if (name != null && !"".equals(name))
                predicateList.add(cb.equal(root.get("name").as(String.class), name));

            if (isbn != null && !"".equals(isbn))
                predicateList.add(cb.equal(root.get("isbn").as(String.class), isbn));

            if (publishedDate != null )
                predicateList.add(cb.greaterThanOrEqualTo(root.get("publishedDate").as(Date.class), publishedDate));

            if (categoryId != null)
                predicateList.add(cb.equal(root.get("category").<Integer> get("id"), categoryId));

            if (authorId != null)
                predicateList.add(cb.equal(root.get("author.id").<Integer> get("id"), authorId));

            if (publisherId != null)
                predicateList.add(cb.equal(root.get("publisher.id").<Integer> get("id"), publisherId));


            Predicate[] pre = new Predicate[predicateList.size()];
            pre = predicateList.toArray(pre);
            return query.where(pre).getRestriction();
        };
        return bookRepo.findAll(specification);
    }

}
