package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.PhysicalBook;

/**
 * A listener which registers with RentBookCommandHandler and is fired on Rental events.
 */
public interface RentalListener {
    /**
     * The listening hook for when a Rental was granted for the physical book indicated.
     * @param physicalBook The physical book which was rented.
     */
    public void rentalGranted(PhysicalBook physicalBook);
}
