package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence;

import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;

public interface LocationRepository {
    
    public Optional<Location> getLocationById(UUID id);
    
}
