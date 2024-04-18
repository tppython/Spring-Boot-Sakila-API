package com.example.sakila.dto.output;

import com.example.sakila.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ActorOutput {
    private Short id;
    private String firstName;
    private String lastName;
    private List<FilmInfoOutput> films;

    public static ActorOutput from(Actor actor) {
        return new ActorOutput(
                actor.getId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getFilms()
                        .stream()
                        .map(FilmInfoOutput::from)
                        .collect(Collectors.toList())
                );
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
