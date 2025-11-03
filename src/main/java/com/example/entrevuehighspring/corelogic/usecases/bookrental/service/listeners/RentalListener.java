package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.RentalState;

/**
 * A listener which registers with RentBookCommandHandler and is fired on Rental events.
 */
public interface RentalListener {
    public void rentalGranted(RentalState rentalState);
}
