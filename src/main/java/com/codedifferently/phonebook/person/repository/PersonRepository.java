package com.codedifferently.phonebook.person.repository;

import com.codedifferently.phonebook.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByLastName();
}
