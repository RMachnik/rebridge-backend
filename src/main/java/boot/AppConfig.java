package boot;

import application.ApplicationConfig;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ApplicationConfig.class, SecurityConfig.class})
public class AppConfig {
}
