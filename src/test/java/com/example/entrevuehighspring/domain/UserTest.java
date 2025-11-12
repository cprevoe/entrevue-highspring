package com.example.entrevuehighspring.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UserTest {

    private UserId getUserId() {
        return UserId.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000000")).build();
    }

    private User getUserWithBirthday(int year, int month, int day) {
        return User.builder().birthday(LocalDate.of(year, month, day)).id(getUserId()).build();
    }

    @Test
    void testGetAgeUserBornToday() {
        // Given a user who was born today
        LocalDate now = LocalDate.now();
        User user = getUserWithBirthday(now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        // When we check their age
        // Then their age is 0
        assertThat(user.getAge())
            .isEqualTo(0);
    }

    @Test
    void testGetAgeUserBornLastYear() {
         // Given a user who was born exactly one year ago
        LocalDate birthdate = LocalDate.now().minusYears(1);
        User user = getUserWithBirthday(birthdate.getYear(), birthdate.getMonthValue(), birthdate.getDayOfMonth());

        // When we check their age
        // Then their age is 1
        assertThat(user.getAge())
            .isEqualTo(1);       
    }

    @Test
    void testGetAgeBornLastYearTomorrow() {
          // Given a user who was born exactly one year ago tomorrow
        LocalDate birthdate = LocalDate.now().plusDays(1).minusYears(1);
        User user = getUserWithBirthday(birthdate.getYear(), birthdate.getMonthValue(), birthdate.getDayOfMonth());

        // When we check their age
        // Then their age is 0
        assertThat(user.getAge())
            .isEqualTo(0);
    }
}
