package com.example.entrevuehighspring.corelogic.usecases.bookrental.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents someone renting books
 */

@Getter @Setter @Builder
public class Renter {
   private UUID id;

   // Might store age instead, but birthday is often kept for account recovery and is dynamic
   private LocalDate birthday;
   private String name; 
   private String emailAddress;

   public long getAge() {
      
      return ChronoUnit.YEARS.between(birthday, LocalDate.now());
      
   }
}
