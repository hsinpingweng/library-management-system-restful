package com.hsinpingweng.library.librarymanagementsystemrestful.repository;

import com.hsinpingweng.library.librarymanagementsystemrestful.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

}
