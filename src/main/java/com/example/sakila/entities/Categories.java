package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "film_category")
@Getter
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Short category;

    @Column(name = "film_id")
    private Short film;
}
