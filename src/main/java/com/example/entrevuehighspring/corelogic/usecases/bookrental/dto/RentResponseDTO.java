package com.example.entrevuehighspring.corelogic.usecases.bookrental.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A Response to a request to rent a book
 */
@Builder @Getter @Setter
public class RentResponseDTO {
    /**
     * isRentGranted indicating whether or not the Rent Request was granted. 
     *
     * Notably, all failure cases are covered by exceptions so currently this 
     * is always true.
     */
    boolean isRentGranted;
}
