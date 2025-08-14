package com.bayzdelivery.controller;

import java.util.List;

import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bayzdelivery.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping()
  public ResponseEntity<Person> register(@RequestBody Person person) {
    return ResponseEntity.ok(personService.save(person));
  }

  @GetMapping()
  public ResponseEntity<List<Person>> getAllPersons() {
    return ResponseEntity.ok(personService.getAll());
  }

  @GetMapping(path = "/{person-id}")
  public ResponseEntity<Person> getPersonById(@PathVariable(name = "person-id") Long personId) {
    Person person = personService.findById(personId);

    if (person != null) {
      return ResponseEntity.ok(person);
    }

    return ResponseEntity.notFound().build();
  }

}
