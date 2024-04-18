package com.example.sakila.dto.output;

import com.example.sakila.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Year;

@Getter
@AllArgsConstructor
public class FilmInfoOutput {
    private Short id;
    private String title;
    private Year releaseYear;

    public static FilmInfoOutput from(Film film) {
        return new FilmInfoOutput(film.getId(), film.getTitle(), film.getReleaseYear());
    }
}
