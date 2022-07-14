package com.codedifferently.phonebook.person.exceptions;

public class PersonNotFoundException extends Exception{
    public PersonNotFoundException(String message) {
        super(message);
    }
}
