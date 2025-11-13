package com.example.entrevuehighspring.domain;

public interface Rentable {
    RentableId getId();
    RentableState getState();
    Location getLocation();
    RentableState rentTo(User user);
}
