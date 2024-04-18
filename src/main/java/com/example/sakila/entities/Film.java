package com.example.sakila.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Year releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

//    @ManyToMany
//    @JoinTable(
//            name = "film_category",
//            joinColumns = {@JoinColumn(name = "film_id")},
//            inverseJoinColumns = {@JoinColumn(name = "category_id")}
//    )
//    private List<Categories> categories = new ArrayList<>();

    @Column(name = "length")
    private Short length;

//    @Column(name = "rental_duration")
//    private Byte rentalDuration;
//
//    @Column(name = "rental_rate")
//    private BigDecimal rentalRate;

    @ManyToMany(mappedBy = "films")
    private List<Actor> cast = new ArrayList<>();
}
