package com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location;

import java.util.Optional;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Renter.Renter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class RentalState {
    private RentalStatus status;
    private Optional<Renter> renter;
}
