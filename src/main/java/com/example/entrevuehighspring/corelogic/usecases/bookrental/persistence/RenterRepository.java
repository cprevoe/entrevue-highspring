package com.example.entrevuehighspring.corelogic.usecases.bookrental.persistence;

import java.util.Optional;
import java.util.UUID;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter;

/**
 * Provides access to known Renters
 */
public interface RenterRepository {

    /**
     * Retrieves a known renter by id
     * @param renterId The id of the renter 
     * @return The known renter with the id provided or Optional.empty()
     */
    public Optional<Renter> getRenterById(UUID renterId);

}
