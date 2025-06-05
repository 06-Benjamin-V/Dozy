package com.example.dozy.listeners;

public interface ValueEventListener<T> {
    void onDataChange(T data);
    void onError(Exception e);
}
