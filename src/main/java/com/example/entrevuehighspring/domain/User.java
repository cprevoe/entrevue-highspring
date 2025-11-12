package com.example.entrevuehighspring.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a user in our system
 */
@Builder
public class User {
    @Getter private UserId id;
    @Getter private LocalDate birthday;


    /**
     * Provides access to the age of this user
     * @return The age of this user based on their birthday.
     */
    public int getAge() {
        return this.getAgeAsOf(LocalDate.now());
    }

    public int getAgeAsOf(LocalDate pointInTime) {
        return this.birthday.until(pointInTime).getYears();
    }
}