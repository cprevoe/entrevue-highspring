package com.example.entrevuehighspring.corelogic.usecases.bookrental.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public class BookRentalResponseDto {
    @Getter private boolean granted;
}
