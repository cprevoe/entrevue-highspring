package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence;

import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;

/**
 * Provides access to known locations
 */
public interface LocationRepository {
    
    /**
     * Retrieve the Location with the ID provided
     * @param id The ID of the desired location
     * @return The known location with the ID provided or Optional.empty()
     */
    public Optional<Location> getLocationById(UUID id);
    
}
