package com.example.entrevuehighspring.corelogic.usecases.bookrental.port;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.util.Pair;

import com.example.entrevuehighspring.domain.Location;
import com.example.entrevuehighspring.domain.Rentable;
import com.example.entrevuehighspring.domain.RentableState;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MockRentableRepository implements RentableRepository {

    private Map<Pair<Location, Rentable>, RentableState> inMemInv = new HashMap<>();

    public void addRentableState(Location location, Rentable rentable, RentableState state) {
        this.inMemInv.put(Pair.of(location, rentable), state);
    }

    @Override
    public Optional<RentableState> getRentableStateAtLocation(Location location, Rentable rentable) {
        return Optional.ofNullable(inMemInv.get(Pair.of(location, rentable)));
    }
}
