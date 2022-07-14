package com.codedifferently.phonebook.person.services;

import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.model.Person;
import com.codedifferently.phonebook.person.repository.PersonRepository;
import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person getPersonById(Integer id) throws PersonNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()){
            logger.error("Person with id {} does not exist", id);
            throw new PersonNotFoundException("Person not found");
        }
        return person.get();
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person updatePerson(Integer id, Person person) throws PersonNotFoundException {
        Optional<Person> optional = personRepository.findById(id);
        if(optional.isEmpty())
            throw new PersonNotFoundException("Person does not exists, can not update");
        Person savedPerson = optional.get();
        savedPerson.setFirstName(person.getFirstName());
        savedPerson.setLastName(person.getLastName());
        savedPerson.setPhoneNumbers(person.getPhoneNumbers());
        return personRepository.save(savedPerson);
    }

    @Override
    public Boolean deletePerson(Integer id) throws PersonNotFoundException {
        Optional<Person> optional = personRepository.findById(id);
        if(optional.isEmpty())
            throw new PersonNotFoundException("Person does not exist, cannot update.");
        Person personToDelete = optional.get();
        personRepository.delete(personToDelete);
        return true;
    }
}
