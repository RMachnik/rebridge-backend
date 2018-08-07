package application.service;

import domain.invitation.Invitation;
import domain.user.EmailAddress;
import lombok.AllArgsConstructor;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;

import static java.lang.String.format;

@AllArgsConstructor
public class MailService {

    static final String WELCOME_MESSAGE = "Hi, you've successfully registered to rebridge.";
    Mailer mailer;

    public void sendWelcomeEmail(EmailAddress recipient) {
        mailer.sendMail(welcomeEmail(recipient), true);
    }

    private org.simplejavamail.email.Email welcomeEmail(EmailAddress recipient) {
        return buildEmail(recipient, "Welcome", WELCOME_MESSAGE);
    }

    private Email buildEmail(EmailAddress recipient, String subject, String message) {
        return EmailBuilder
                .startingBlank()
                .from("Rebridge", "doNotReply@mail.com")
                .to(recipient.getValue())
                .withSubject(subject)
                .withPlainText(message)
                .buildEmail();
    }

    public void sendInvitation(Invitation invitation) {
        mailer.sendMail(buildEmail(
                invitation.getEmailAddress(),
                "Invitation",
                format("Hi, please join this project. Here is the url %s.", invitation.getRedirection())
                )
        );
    }
}
