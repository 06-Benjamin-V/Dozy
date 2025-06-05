package com.example.dozy.model;

import com.google.firebase.Timestamp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

    private Task task;
    private Timestamp timestamp;

    @Before
    public void setUp() {
        timestamp = Timestamp.now();
        task = new Task("1", "Title", "Description", timestamp, true, "user123");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("1", task.getId());
        assertEquals("Title", task.getTitle());
        assertEquals("Description", task.getDescription());
        assertEquals(timestamp, task.getDeadline());
        assertTrue(task.isCompleted());
        assertEquals("user123", task.getUserId());
    }

    @Test
    public void testSetters() {
        Timestamp newTimestamp = Timestamp.now();

        task.setId("2");
        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setDeadline(newTimestamp);
        task.setCompleted(false);
        task.setUserId("user456");

        assertEquals("2", task.getId());
        assertEquals("New Title", task.getTitle());
        assertEquals("New Description", task.getDescription());
        assertEquals(newTimestamp, task.getDeadline());
        assertFalse(task.isCompleted());
        assertEquals("user456", task.getUserId());
    }

    @Test
    public void testEmptyConstructor() {
        Task emptyTask = new Task();
        assertNull(emptyTask.getId());
        assertNull(emptyTask.getTitle());
        assertNull(emptyTask.getDescription());
        assertNull(emptyTask.getDeadline());
        assertFalse(emptyTask.isCompleted());
        assertNull(emptyTask.getUserId());
    }
}
