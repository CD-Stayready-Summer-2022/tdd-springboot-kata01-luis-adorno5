package com.codedifferently.phonebook.person.model;

import com.codedifferently.phonebook.phonenumber.model.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person emptyPerson1;
    private Person emptyPerson2;

    private Person person1;
    private Person person2;

    @BeforeEach
    void setUp() {
        emptyPerson1 = new Person();
        emptyPerson2 = new Person();

        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("787-123-4567", true));
        numbers.add(new PhoneNumber("787-321-7654", true));

        person1 = new Person("Luis", "Adorno", numbers);
        person2 = new Person("Johny", "Adorno", numbers);
        person1.setId(1);
        person2.setId(2);
    }

    @Test
    public void testEmptyEquals() throws Exception {

        assertTrue(
                emptyPerson1.equals(emptyPerson2));
    }

    @Test
    public void testNotEquals() throws Exception {

        assertFalse(
                emptyPerson1.equals(person2));
    }

    @Test
    public void testEmptyHashCode() throws Exception {

        assertEquals(
                emptyPerson1.hashCode(),
                emptyPerson2.hashCode());
    }

    @Test
    public void testHashCode() throws Exception {

        assertNotEquals(
                emptyPerson1.hashCode(),
                person1.hashCode());
    }

    @Test
    public void testEmptyToString() throws Exception {

        assertEquals(
                emptyPerson1.toString(),
                emptyPerson2.toString());
    }

    @Test
    public void testNotToString() throws Exception {

        assertNotEquals(
                emptyPerson1.toString(),
                person2.toString());
    }
}
