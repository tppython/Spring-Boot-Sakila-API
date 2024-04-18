package com.example.sakila.dto.output;

//import com.example.sakila.entities.Categories;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

//import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class FilmOutput {
    private Short id;
    private String title;
    private String description;
    private Year releaseYear;
    private Language language;
//    private Categories categories;
    private Short length;
//    private Byte rentalDuration;
//    private BigDecimal rentalRate;
    private List<ActorInfoOutput> cast;

    public static FilmOutput from(Film film) {
        return new FilmOutput(
                film.getId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage(),
//                film.getCategories(),
                film.getLength(),
//                film.getRentalDuration(),
//                film.getRentalRate(),
                film.getCast().stream()
                        .map(ActorInfoOutput::from)
                        .collect(Collectors.toList())
        );
    }
}