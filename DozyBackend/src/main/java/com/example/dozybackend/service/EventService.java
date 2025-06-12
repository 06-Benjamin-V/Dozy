package com.example.dozybackend.service;

import com.example.dozybackend.dao.Event;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class EventService {
    private final Firestore firestore;

    public String createEvent(String userId, Event event) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users")
                .document(userId)
                .collection("events")
                .document();
        event.setId(docRef.getId());
        ApiFuture<WriteResult> result = docRef.set(event);
        result.get();
        return "Event created with ID: " + event.getId();
    }

    public Event getEvent(String userId, String eventId) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection("users")
                .document(userId)
                .collection("events")
                .document(eventId)
                .get()
                .get();
        return snapshot.exists() ? snapshot.toObject(Event.class) : null;
    }

    public String updateEvent(String userId, String eventId, Event event) throws ExecutionException, InterruptedException {
        event.setId(eventId);
        ApiFuture<WriteResult> result = firestore.collection("users")
                .document(userId)
                .collection("events")
                .document(eventId)
                .set(event);
        result.get();
        return "Event updated with ID: " + eventId;
    }

    public String deleteEvent(String userId, String eventId) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> result = firestore.collection("users")
                .document(userId)
                .collection("events")
                .document(eventId)
                .delete();
        result.get();
        return "Event deleted with ID: " + eventId;
    }

    public List<Event> getEventsByUser(String userId) throws ExecutionException, InterruptedException {
        List<Event> events = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("users")
                .document(userId)
                .collection("events")
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for(QueryDocumentSnapshot doc : documents) {
            events.add(doc.toObject(Event.class));
        }
        return events;
    }
}
