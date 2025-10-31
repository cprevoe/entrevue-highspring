package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;

@Builder
public class LocationRepository {
    @Builder.Default
    private Map<UUID, Location> locations = new HashMap<UUID, Location>();

    public Optional<Location> getLocationById(UUID id) {
        return Optional.ofNullable(locations.get(id));
    }
}
