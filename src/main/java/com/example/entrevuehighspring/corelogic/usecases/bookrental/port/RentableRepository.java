package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.Optional;

import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.Rentable;
import com.example.entrevuehighspring.domain.RentableState;

/**
 * The port for accessing RentableState from some store.
 */
public interface RentableRepository {
    Optional<RentableState> getRentableStateAtLocation(Location location, Rentable rentable);
}
