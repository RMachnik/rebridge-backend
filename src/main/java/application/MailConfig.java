package application;

import application.service.MailService;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Value("${mail.host}")
    String host;
    @Value("${mail.username}")
    String username;
    @Value("${mail.password}")
    String password;
    @Value("${mail.port}")
    Integer port;


    @Bean
    MailService mailService() {
        Mailer mailer = MailerBuilder.withSMTPServer(host, port, username, password).buildMailer();
        return new MailService(mailer);
    }
}
