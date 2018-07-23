package application.rest;

import application.dto.AuthDto;
import boot.Application;
import org.cassandraunit.spring.CassandraUnit;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.PostConstruct;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Configuration
@TestExecutionListeners(
        listeners = {CassandraUnitTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
        mergeMode = MERGE_WITH_DEFAULTS
)
@CassandraUnit
@Import(Application.class)
public class ApplicationTestConfig {

    @Autowired
    AuthController authController;

    @PostConstruct
    void createTestUser() {
        authController.register(new AuthDto("test@email.com", "password"));
    }
}
