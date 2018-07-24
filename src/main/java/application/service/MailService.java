package application.service;

import domain.user.EmailAddress;
import lombok.AllArgsConstructor;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;

@AllArgsConstructor
public class MailService {

    Mailer mailer;

    public void sendWelcomeEmail(EmailAddress receipient) {
        mailer.sendMail(welcomeEmail(receipient), true);
    }

    private org.simplejavamail.email.Email welcomeEmail(EmailAddress recipient) {
        return EmailBuilder
                .startingBlank()
                .from("Rebridge", "doNotReply@mail.com")
                .to(recipient.getValue())
                .withSubject("Welcome")
                .withPlainText("Hi, you've successfully registered to rebridge.")
                .buildEmail();
    }
}
