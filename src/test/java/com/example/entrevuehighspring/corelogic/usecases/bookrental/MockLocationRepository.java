package com.example.entrevuehighspring.corelogic.usecases.bookrental;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.LocationRepository;
import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.LocationId;

public class MockLocationRepository implements LocationRepository{

    private Map<LocationId, Location> locationInventory = new HashMap<>();

    public void add(Location location) {
        this.locationInventory.put(location.getId(), location);
    }

    public Optional<Location> getLocationById(LocationId locationId) {
        return Optional.ofNullable(locationInventory.get(locationId));
    }
}
