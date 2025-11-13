package com.example.entrevuehighspring.domain;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class MockRentableState implements RentableState {
    @Getter @Setter private Rentable rentable;
    @Getter @Setter private RentableStatus status;
    @Getter @Setter private Optional<User> borrower;
}
