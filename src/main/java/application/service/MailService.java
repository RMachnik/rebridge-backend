package application.service;

import domain.user.Email;
import lombok.AllArgsConstructor;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;

@AllArgsConstructor
public class MailService {

    Mailer mailer;

    public void sendWelcomeEmail(Email receipient) {
        mailer.sendMail(welcomeEmail(receipient), true);
    }

    private org.simplejavamail.email.Email welcomeEmail(Email receipient) {
        return EmailBuilder
                .startingBlank()
                .from("Rebridge", "doNotReply@mail.com")
                .to(receipient.getValue())
                .withSubject("Welcome")
                .withPlainText("Hi, you've successfully registered to rebridge.")
                .buildEmail();
    }
}
