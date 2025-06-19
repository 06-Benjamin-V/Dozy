package com.example.dozybackend.dao;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Data;

@Data
public class Task {
    @DocumentId
    private String id;
    private String userId;
    private String title;
    private String description;
    private boolean completed;
    private Timestamp deadline;

    @ServerTimestamp
    private Timestamp createdAt;
}
