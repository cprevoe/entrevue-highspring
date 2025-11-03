package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NonSendingEmailSender implements EmailSender {

    @Override
    public void sendEmail(String toAddress, String subject, String body) {
        log.info("Email queued for sending.");
    }
}
