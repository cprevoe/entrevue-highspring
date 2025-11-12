package com.example.entrevuehighspring.adapter.outgoing;

import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.LocationId;

public interface LocationRepository {
    Location getLocationById(LocationId locationId);
}
