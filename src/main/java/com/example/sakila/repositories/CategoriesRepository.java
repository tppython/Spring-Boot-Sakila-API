package com.example.sakila.repositories;

import com.example.sakila.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Byte> {
}
