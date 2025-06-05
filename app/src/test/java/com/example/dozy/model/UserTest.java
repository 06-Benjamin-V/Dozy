package com.example.dozy.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User("johndoe", "123456789", "johndoe@example.com");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("johndoe", user.getUsername());
        assertEquals("123456789", user.getPhone());
        assertEquals("johndoe@example.com", user.getEmail());
    }

    @Test
    public void testSetters() {
        user.setUsername("janedoe");
        user.setPhone("987654321");
        user.setEmail("janedoe@example.com");

        assertEquals("janedoe", user.getUsername());
        assertEquals("987654321", user.getPhone());
        assertEquals("janedoe@example.com", user.getEmail());
    }

    @Test
    public void testEmptyConstructor() {
        User emptyUser = new User();
        assertNull(emptyUser.getUsername());
        assertNull(emptyUser.getPhone());
        assertNull(emptyUser.getEmail());
    }
}
