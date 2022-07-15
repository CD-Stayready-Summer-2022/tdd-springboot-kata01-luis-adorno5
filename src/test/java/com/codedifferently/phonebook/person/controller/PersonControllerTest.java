package com.codedifferently.phonebook.person.controller;

import com.codedifferently.phonebook.BaseControllerTest;
import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.model.Person;
import com.codedifferently.phonebook.person.services.PersonService;
import com.codedifferently.phonebook.phonenumber.model.PhoneNumber;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerTest extends BaseControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

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

        mockResponsePerson2 = new Person("Arturo", "Adorno", numbers);
        mockResponsePerson2.setId(2);
    }

    @Test
    @DisplayName("Person Post: /persons - success")
    public void createPersonRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponsePerson1).when(personService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputPerson)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("Luis")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is("Adorno")));
    }

    @Test
    @DisplayName("GET /persons/1 - Found")
    public void getPersonByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponsePerson1).when(personService).getPersonById(1);

        mockMvc.perform(get("/persons/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Luis")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is("Adorno")));
    }

    @Test
    @DisplayName("GET /persons/1 - Not Found")
    public void getPersonByIdTestFail() throws Exception {
        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(personService).getPersonById(1);
        mockMvc.perform(get("/persons/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /persons/1 - Success")
    public void putPersonTestNotSuccess() throws Exception{
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("787-123-7891", true));
        numbers.add(new PhoneNumber("787-321-1987", true));
        Person expectedPersonUpdate = new Person("Tony", "Cruz", numbers);
        expectedPersonUpdate.setId(1);
        BDDMockito.doReturn(expectedPersonUpdate).when(personService).updatePerson(any(), any());
        mockMvc.perform(put("/persons/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputPerson)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Tony")))
                .andExpect(jsonPath("$.lastName", Is.is("Cruz")));
    }

    @Test
    @DisplayName("PUT /persons/1 - Not Found")
    public void putPersonTestNotFound() throws Exception{
        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(personService).updatePerson(any(), any());
        mockMvc.perform(put("/persons/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputPerson)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /persons/1 - Success")
    public void deletePersonTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(personService).deletePerson(any());
        mockMvc.perform(delete("/persons/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /persons/1 - Not Found")
    public void deletePersonTestNotFound() throws Exception{
        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(personService).deletePerson(any());
        mockMvc.perform(delete("/persons/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
