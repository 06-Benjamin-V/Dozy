package com.example.dozybackend.controller;

import com.example.dozybackend.dao.Event;
import com.example.dozybackend.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users/{userId}/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<String> createEvent(@PathVariable String userId, @RequestBody Event event) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(eventService.createEvent(userId, event));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable String userId, @PathVariable String eventId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(eventService.getEvent(userId,eventId));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable String userId, @PathVariable String eventId, @RequestBody Event event) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(eventService.updateEvent(userId,eventId,event));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable String userId, @PathVariable String eventId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(eventService.deleteEvent(userId,eventId));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(eventService.getEventsByUser(userId));
    }
}
