package com.example.dozybackend.service;

import com.example.dozybackend.dao.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference usersCollection;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private ApiFuture<DocumentReference> apiFuture;

    @Mock
    private ApiFuture<DocumentSnapshot> snapshotFuture;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(firestore);
    }

    @Test
    void createUser_success() throws Exception {
        User user = new User();

        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.add(user)).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(documentReference);
        when(documentReference.getId()).thenReturn("123");

        String result = userService.createUser(user);
        assertTrue(result.contains("123"));
    }

    @Test
    void getUser_success() throws Exception {
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("123")).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(snapshotFuture);
        when(snapshotFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.toObject(User.class)).thenReturn(new User());

        User user = userService.getUser("123");
        assertNotNull(user);
    }

    @Test
    void updateUser_success() {
        User user = new User();
        user.setId("123");

        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("123")).thenReturn(documentReference);

        String result = userService.updateUser("123", user);
        assertTrue(result.contains("123"));
    }

    @Test
    void deleteUser_success() {
        when(firestore.collection("users")).thenReturn(usersCollection);
        when(usersCollection.document("123")).thenReturn(documentReference);

        String result = userService.deleteUser("123");
        assertTrue(result.contains("123"));
    }
}
