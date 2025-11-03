package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

/**
 * An email sender service.
 */
public interface EmailSender {
    
    /**
     * Queues an email with the details provided to be sent asynchronously.
     * 
     * @param toAddress the address to send the email to
     * @param subject The subject of the email
     * @param body The body of the email
     */
    public void sendEmail(String toAddress, String subject, String body);

}
