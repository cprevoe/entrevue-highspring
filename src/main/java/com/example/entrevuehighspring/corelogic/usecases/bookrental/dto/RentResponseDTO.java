package com.example.entrevuehighspring.corelogic.usecases.bookrental.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class RentResponseDTO {
    boolean isRentPermitted;
}
