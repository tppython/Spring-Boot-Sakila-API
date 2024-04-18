package com.example.sakila.controllers;

import com.example.sakila.dto.input.ActorInput;
import com.example.sakila.dto.output.ActorOutput;
import com.example.sakila.entities.Actor;
import com.example.sakila.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sakila.dto.input.ValidationGroup.Create;
import static com.example.sakila.dto.input.ValidationGroup.Update;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    // Method for Getting all actors from the database
    @GetMapping
    public List<ActorOutput> readAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        final var actors = actorRepository.findAll();
        return actors.stream()
                .map(ActorOutput::from)
                .collect(Collectors.toList());
    }

    // Method for Getting an actor from the database, using their ID
    @GetMapping("/{id}")
    public ActorOutput readById(@PathVariable Short id) {
        return actorRepository.findById(id)
                .map(ActorOutput::from)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No such actor with id %d.", id)
        ));
    }

    // Method for Creating an actor, and using Post to add this to database
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorOutput create(@Validated(Create.class) @RequestBody ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        final var saved = actorRepository.save(actor);
        return ActorOutput.from(saved);
    }

    // Method for Updating an actor, using Patch to update the database
    @PatchMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActorOutput update(@Validated(Update.class) @RequestBody ActorInput data, @PathVariable Short id) {
        var actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such actor with id %d.", id)
                ));
        if (data.getFirstName() != null) {
            actor.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null) {
            actor.setLastName(data.getLastName());
        }

        final var saved = actorRepository.save(actor);
        return ActorOutput.from(saved);
    }

    // Method for deleting an actor from the database using the ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Short id) {
        actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such actor with id %d.", id)
                ));
        actorRepository.deleteById(id);
    }
}
