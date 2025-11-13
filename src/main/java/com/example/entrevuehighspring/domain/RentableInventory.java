package com.example.entrevuehighspring.domain;

import java.util.Optional;

public interface RentableInventory {

    /**
     * Determines if the rentable is available at the Location provided
     * @param location The location to rent from
     * @param rentable The rentable object desired
     * @return The RentalState of the object if it's available at this location, or Optional.empty()
     */
    Optional<RentableState> getRentableState(Location location, Rentable rentable);

}