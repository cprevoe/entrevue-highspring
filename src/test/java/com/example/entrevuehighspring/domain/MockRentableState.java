package com.example.entrevuehighspring.domain;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Builder
public class MockRentableState implements RentableState {
    @Getter private Rentable rentable;
    @Getter private RentableStatus status;
    @Getter private Optional<User> borrower;
}
