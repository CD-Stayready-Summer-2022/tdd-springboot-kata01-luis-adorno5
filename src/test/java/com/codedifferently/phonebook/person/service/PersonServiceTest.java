package com.codedifferently.phonebook.person.service;

import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.model.Person;
import com.codedifferently.phonebook.person.repository.PersonRepository;
import com.codedifferently.phonebook.person.services.PersonService;
import com.codedifferently.phonebook.phonenumber.model.PhoneNumber;
import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @MockBean
    private PersonRepository mockPersonRepo;

    @Autowired
    private PersonService personService;

    private Person inputPerson;
    private Person mockResponsePerson1;
    private Person mockResponsePerson2;

    @BeforeEach
    public void setUp() {
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("787-123-4567", true));
        numbers.add(new PhoneNumber("787-321-7654", true));

        inputPerson = new Person("Luis", "Adorno", numbers);

        mockResponsePerson1 = new Person("Luis", "Adorno", numbers);
        mockResponsePerson1.setId(1);

        mockResponsePerson2 = new Person("Jonathan", "Adorno", numbers);
        mockResponsePerson2.setId(2);
    }

    @Test
    @DisplayName("Person Service: Create Person - Success")
    public void createPersonTestSuccess(){
        BDDMockito.doReturn(mockResponsePerson1).when(mockPersonRepo).save(ArgumentMatchers.any());
        Person returnedPerson = personService.create(inputPerson);
        Assertions.assertNotNull(returnedPerson, "Person should not be null");
        Assertions.assertEquals(returnedPerson.getId(),1 );
    }

    @Test
    @DisplayName("Person Service: Get Person by Id - Success")
    public void getPersonByIdTestSuccess() throws PersonNotFoundException {
        BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
        Person foundPerson = personService.getPersonById(1);
        Assertions.assertEquals(mockResponsePerson1.toString(), foundPerson.toString());
    }

    @Test
    @DisplayName("Person Service: Get Person by Id - Fail")
    public void getPersonByIdTestFailed() {
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.getPersonById(1);
        });
    }

    @Test
    @DisplayName("Person Service: Get All Persons - Success")
    public void getAllPersonsTestSuccess(){
        List<Person> persons = new ArrayList<>();
        persons.add(mockResponsePerson1);
        persons.add(mockResponsePerson2);

        BDDMockito.doReturn(persons).when(mockPersonRepo).findAll();

        List<Person> responsePersons = personService.getAllPersons();
        Assertions.assertIterableEquals(persons,responsePersons);
    }

    @Test
    @DisplayName("Person Service: Update Person - Success")
    public void updatePersonTestSuccess() throws WidgetNotFoundException, PersonNotFoundException {
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("123-456-7891", true));
        numbers.add(new PhoneNumber("321-654-1987", true));
        Person expectedPersonUpdate = new Person("Tato", "Ortiz", numbers);

        BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
        BDDMockito.doReturn(expectedPersonUpdate).when(mockPersonRepo).save(ArgumentMatchers.any());

        Person actualWidget = personService.updatePerson(1, expectedPersonUpdate);
        Assertions.assertEquals(expectedPersonUpdate.toString(), actualWidget.toString());
    }

    @Test
    @DisplayName("Person Service: Update Person - Fail")
    public void updatePersonTestFail()  {
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("123-456-7891", true));
        numbers.add(new PhoneNumber("321-654-1987", true));
        Person expectedPersonUpdate = new Person("Tato", "Ortiz", numbers);
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, ()-> {
            personService.updatePerson(1, expectedPersonUpdate);
        });
    }

    @Test
    @DisplayName("Person Service: Delete Person - Success")
    public void deletePersonTestSuccess() throws PersonNotFoundException {
        BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
        Boolean actualResponse = personService.deletePerson(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Widget Service: Delete Widget - Fail")
    public void deletePersonTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, ()-> {
            personService.deletePerson(1);
        });
    }
}
