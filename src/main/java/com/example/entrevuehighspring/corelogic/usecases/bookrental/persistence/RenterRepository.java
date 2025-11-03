package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence;

import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;

public interface RenterRepository {

    public Optional<Renter> getRenterById(UUID renterId);

}
