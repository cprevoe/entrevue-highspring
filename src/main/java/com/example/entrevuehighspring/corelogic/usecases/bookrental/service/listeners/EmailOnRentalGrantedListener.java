package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.RentalState;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.EmailSender;

import lombok.Builder;

/**
 * Listener for sending emails upon Rental Requests being granted.
 */
@Builder
public class EmailOnRentalGrantedListener implements RentalListener {

    /**
     * Our email service. We assume calls to this are async to avoid holding
     * up the request.
     */
    @Autowired EmailSender emailSender;

    /**
     * Helper method for generating the subject of the email.
     * 
     * @param rentalState The RentalState this email is concerning.
     * @return A subject for the email which will be sent. Could be localized here.
     */
    private String generateSubject(RentalState rentalState) {
        return "Reasonable Subject Goes Here";
    }


    /**
     * Helper method for generating the email body of the email
     * @param rentalState The RentalState this email is concerning.
     * @return The body of the email which will be sent. Could also be localized here.
     */
    private String generateEmailBody(RentalState rentalState) {
        return "This is where you'd likely have a templating service or something to generate the body of the email.";
    }


    /**
     * Hook method which queues an email to be sent when the rental is granted
     */
    public void rentalGranted(RentalState rentalState) {
        // Note: This should always be populated when the rental was granted.
        String toAddress = rentalState.getRenter().get().getEmailAddress();

        emailSender.sendEmail(toAddress, generateSubject(rentalState) , generateEmailBody(rentalState));
    };
}
