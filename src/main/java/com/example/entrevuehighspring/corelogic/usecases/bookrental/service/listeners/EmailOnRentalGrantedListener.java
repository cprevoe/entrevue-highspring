package com.example.entrevuehighspring.corelogic.usecases.bookrental.service.listeners;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entrevuehighspring.corelogic.usecases.bookrental.model.Location.RentalState;
import com.example.entrevuehighspring.corelogic.usecases.bookrental.service.EmailSender;

public class EmailOnRentalGrantedListener implements RentalListener {

    @Autowired EmailSender emailSender;

    private String generateSubject(RentalState rentalState) {
        return "Reasonable Subject Goes Here";
    }

    private String generateEmailBody(RentalState rentalState) {
        return "This is where you'd likely have a templating service or something to generate the body of the email.";
    }

    public void rentalGranted(RentalState rentalState) {
        // Note: This should always be populated when the rental was granted.
        String toAddress = rentalState.getRenter().get().getEmailAddress();

        emailSender.sendEmail(toAddress, generateSubject(rentalState) , generateEmailBody(rentalState));
    };
}
