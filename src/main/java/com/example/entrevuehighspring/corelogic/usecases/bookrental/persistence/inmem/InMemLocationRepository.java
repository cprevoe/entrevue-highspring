package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.inmem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence.LocationRepository;

import lombok.Builder;

@Builder
public class InMemLocationRepository implements LocationRepository {
    @Builder.Default
    private Map<UUID, Location> locations = new HashMap<UUID, Location>();

    public Optional<Location> getLocationById(UUID id) {
        return Optional.ofNullable(locations.get(id));
    }
}
