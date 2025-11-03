package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.RenterRepository;

import lombok.Builder;

@Builder
public class InMemRenterRepository implements RenterRepository {
    private Map<UUID, Renter> renters;

    public Optional<Renter> getRenterById(UUID renterId) {
        return Optional.ofNullable(renters.get(renterId));
    }
}
