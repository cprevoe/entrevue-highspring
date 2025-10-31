package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;

@Builder
public class RenterRepository {
    private Map<UUID, Renter> renters;

    public Optional<Renter> getRenterById(UUID renterId) {
        return Optional.ofNullable(renters.get(renterId));
    }
}
