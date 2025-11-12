package com.example.entrevuehighspring.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
public class RentableState {
    public static enum Status {
        AVAILABLE,
        BORROWED
    };

    @Getter private Rentable rentable;
    @Getter private Status status;   
}
