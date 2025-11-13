package com.example.entrevuehighspring.domain;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class MockBook implements Book {
    private BookId id;
    @Getter private BookType type;
    @Getter @Setter private MockRentableState state;
    @Getter @Setter private MockLocation location;

    @Builder.Default
    @Getter private int minAge = 0;

    @Override
    public RentableId getId() {
        return id;
    }

    @Override
    public RentableState rentTo(User user) {
        state.setBorrower(Optional.of(user));
        state.setStatus(RentableStatus.BORROWED);

        return state;
    }

}