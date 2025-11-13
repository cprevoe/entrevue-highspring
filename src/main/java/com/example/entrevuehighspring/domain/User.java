package com.example.entrevuehighspring.domain;

import java.time.LocalDate;

public interface User {

    UserId getId();

    LocalDate getBirthday();

    /**
     * Provides access to the age of this user
     * @return The age of this user based on their birthday.
     */
    int getAge();

}