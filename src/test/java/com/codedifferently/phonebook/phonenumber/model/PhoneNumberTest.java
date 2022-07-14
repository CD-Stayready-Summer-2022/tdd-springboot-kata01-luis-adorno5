package com.codedifferently.phonebook.phonenumber.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhoneNumberTest {

    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;

    private PhoneNumber emptyPhoneNumber1;
    private PhoneNumber emptyPhoneNumber2;

    @BeforeEach
    void setUp() {
        emptyPhoneNumber1 = new PhoneNumber();
        emptyPhoneNumber2 = new PhoneNumber();

        phoneNumber1 = new PhoneNumber("787-123-4567", true);
        phoneNumber2 = new PhoneNumber("787-321-7654", true);
        phoneNumber1.setId(1);
        phoneNumber2.setId(2);
    }

    @Test
    public void testEmptyEquals() throws Exception{
        Assertions.assertTrue(emptyPhoneNumber1.equals(emptyPhoneNumber2));
    }

    @Test
    public void testNotEquals() throws Exception{
        Assertions.assertFalse(phoneNumber1.equals(phoneNumber2));
    }

    @Test
    public void testEmptyHashCode() {
        Assertions.assertEquals(emptyPhoneNumber1.hashCode(), emptyPhoneNumber2.hashCode());
    }

    @Test
    public void testContentHashCode(){
        Assertions.assertNotEquals(phoneNumber1.hashCode(), phoneNumber2.hashCode());
    }

    @Test
    public void testEmptyToString(){
        Assertions.assertEquals(emptyPhoneNumber1.toString(), emptyPhoneNumber2.toString());
    }

    @Test
    public void testToString(){
        Assertions.assertFalse(emptyPhoneNumber1.toString().equals(phoneNumber1.toString()));
    }
}
