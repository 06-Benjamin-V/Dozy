package com.example.dozybackend.dao;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;



@Data
public class Event {
    @DocumentId
    private String id;
    private String title;
    private String description;
    private String date;

}
