package com.example.dozybackend.dao;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Data;

@Data
public class User {
    @DocumentId
    private String id;
    private String username;
    private String email;
    private String phone;

    @ServerTimestamp
    private Timestamp createdAT;
}
