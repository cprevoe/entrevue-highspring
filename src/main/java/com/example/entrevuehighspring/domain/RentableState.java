package com.example.entrevuehighspring.domain;

import java.util.Optional;

public interface RentableState {

    Rentable getRentable();

    RentableStatus getStatus();

    Optional<User> getBorrower();

}