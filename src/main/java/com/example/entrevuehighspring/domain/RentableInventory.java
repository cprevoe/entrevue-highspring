package com.example.entrevuehighspring.domain;

import java.util.Optional;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.port.RentableRepository;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents the business concept of an inventory of rentables at a given location. 
 */
@Builder
public class RentableInventory {

    private RentableRepository rentableRepository;

    /**
     * Determines if the rentable is available at the Location provided
     * @param location The location to rent from
     * @param rentable The rentable object desired
     * @return The RentalState of the object if it's available at this location, or Optional.empty()
     */
    Optional<RentableState> getRentableState(Location location, Rentable rentable) {
        return Optional.empty();
    }
}
