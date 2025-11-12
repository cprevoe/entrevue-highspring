package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.Optional;

import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.LocationId;

public interface LocationRepository {

    public Optional<Location> getLocationById(LocationId locationId);

}
