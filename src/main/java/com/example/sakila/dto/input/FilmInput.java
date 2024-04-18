package com.example.sakila.dto.input;

import com.example.sakila.entities.Language;
import lombok.Data;

import java.time.Year;

@Data
public class FilmInput {
    private String title;

    private String description;

    private Year releaseYear;

    private Byte language;
}
