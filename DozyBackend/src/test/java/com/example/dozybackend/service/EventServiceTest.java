package com.example.dozybackend.service;

import com.example.dozybackend.dao.Event;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private Firestore firestore;
    @Mock
    private CollectionReference usersCollection;
    @Mock
    private DocumentReference userDoc;
    @Mock
    private CollectionReference eventsCollection;
    @Mock
    private DocumentReference eventDoc;
    @Mock
    private ApiFuture<WriteResult> writeResultFuture;
    @Mock
    private ApiFuture<DocumentSnapshot> docSnapshotFuture;
    @Mock
    private DocumentSnapshot docSnapshot;
    @Mock
    private ApiFuture<QuerySnapshot> querySnapshotFuture;
    @Mock
    private QuerySnapshot querySnapshot;
    @Mock
    private QueryDocumentSnapshot queryDocumentSnapshot;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(firestore);
    }

    @Test
    void createEvent_returnsMessage() throws Exception {
        Event event = new Event();
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("user123")).thenReturn(userDoc);
        when(userDoc.collection("events")).thenReturn(eventsCollection);
        when(eventsCollection.document()).thenReturn(eventDoc);
        when(eventDoc.set(any(Event.class))).thenReturn(writeResultFuture);

        String result = eventService.createEvent("user123", event);

        assertTrue(result.contains("Event created with ID:"));
        verify(eventDoc).set(event);
    }

    @Test
    void getEvent_returnsEvent() throws Exception {
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("user123")).thenReturn(userDoc);
        when(userDoc.collection("events")).thenReturn(eventsCollection);
        when(eventsCollection.document("event123")).thenReturn(eventDoc);
        when(eventDoc.get()).thenReturn(docSnapshotFuture);
        when(docSnapshotFuture.get()).thenReturn(docSnapshot);
        when(docSnapshot.exists()).thenReturn(true);
        when(docSnapshot.toObject(Event.class)).thenReturn(new Event());

        Event event = eventService.getEvent("user123", "event123");
        assertNotNull(event);
    }

    @Test
    void updateEvent_returnsMessage() throws Exception {
        Event event = new Event();
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("user123")).thenReturn(userDoc);
        when(userDoc.collection("events")).thenReturn(eventsCollection);
        when(eventsCollection.document("event123")).thenReturn(eventDoc);
        when(eventDoc.set(event)).thenReturn(writeResultFuture);

        String result = eventService.updateEvent("user123", "event123", event);
        assertTrue(result.contains("Event updated with ID:"));
    }

    @Test
    void deleteEvent_returnsMessage() throws Exception {
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("user123")).thenReturn(userDoc);
        when(userDoc.collection("events")).thenReturn(eventsCollection);
        when(eventsCollection.document("event123")).thenReturn(eventDoc);
        when(eventDoc.delete()).thenReturn(writeResultFuture);

        String result = eventService.deleteEvent("user123", "event123");
        assertTrue(result.contains("Event deleted with ID:"));
    }

    @Test
    void getEventsByUser_returnsList() throws Exception {
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("user123")).thenReturn(userDoc);
        when(userDoc.collection("events")).thenReturn(eventsCollection);
        when(eventsCollection.get()).thenReturn(querySnapshotFuture);
        when(querySnapshotFuture.get()).thenReturn(querySnapshot);
        when(querySnapshot.getDocuments()).thenReturn(Arrays.asList(queryDocumentSnapshot));
        when(queryDocumentSnapshot.toObject(Event.class)).thenReturn(new Event());

        List<Event> events = eventService.getEventsByUser("user123");
        assertEquals(1, events.size());
    }
}
