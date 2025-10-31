package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.RentalState;

public interface RentalListener {
    public void rentalGranted(RentalState rentalState);
}
