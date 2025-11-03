package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import lombok.Getter;

/**
 * A mock email service which counts the number of emails its received.
 */
public class MockEmailSender implements EmailSender {
    @Getter
    int count = 0;

    @Override
    public void sendEmail(String toAddress, String subject, String body) {
        count++;
    }
}
