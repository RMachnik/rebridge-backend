package boot;

import application.ApplicationConfig;
import infrastructure.cassandra.CassandraConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import security.SecurityConfig;

@SpringBootApplication
@Import({CassandraConfig.class, ApplicationConfig.class, SecurityConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}