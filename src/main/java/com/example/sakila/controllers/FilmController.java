package com.example.sakila.controllers;


import com.example.sakila.dto.input.FilmInput;
import com.example.sakila.dto.output.FilmOutput;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import com.example.sakila.repositories.CategoriesRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sakila.dto.input.ValidationGroup.Create;
import static com.example.sakila.dto.input.ValidationGroup.Update;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;

    // Method for Getting all films from the database
    @GetMapping
    public List<FilmOutput> readAll() {
        final var films = filmRepository.findAll();
            return films.stream()
                .map(FilmOutput::from)
                .collect(Collectors.toList());
    }

    // Method for Getting a film from the database, using its ID
    @GetMapping("/{id}")
    public FilmOutput readById(@PathVariable Short id) {
        return filmRepository.findById(id)
                .map(FilmOutput::from)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No such film with id %d", id)
        ));
    }

    // Method for Creating a film, and using Post to add this to the database
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmOutput create(@Validated(Create.class) @RequestBody FilmInput data) {
        final var film = new Film();
        film.setTitle(data.getTitle());
        film.setDescription(data.getDescription());
        film.setReleaseYear(data.getReleaseYear());

        Language languageID = languageRepository.findById(data.getLanguage())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No such language with id %d.", data.getLanguage())

                        ));
        film.setLanguage(languageID);

        final var saved = filmRepository.save(film);
        return FilmOutput.from(saved);
    }

    // Method for Updating a film, using Patch to update the database
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmOutput update(@Validated(Update.class) @RequestBody FilmInput data, @PathVariable Short id) {
        var film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such film with id %d.", id)
                ));

        if (data.getTitle() != null) {
            film.setTitle(data.getTitle());
        }
        if (data.getDescription() != null) {
            film.setDescription(data.getDescription());
        }
        if (data.getReleaseYear() != null) {
            film.setReleaseYear(data.getReleaseYear());
        }
        if (data.getLanguage() != null) {
            Language languageID = languageRepository.findById(data.getLanguage())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("No such language with id %d.", data.getLanguage())

                    ));
            film.setLanguage(languageID);
        }

        final var saved = filmRepository.save(film);
        return FilmOutput.from(saved);
    }

    // Method for Deleting a film from the database using the ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Short id) {
        filmRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No such film with id %d.", id)
                        ));
        filmRepository.deleteById(id);
    }
}
