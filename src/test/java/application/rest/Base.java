package application.rest;

import application.dto.CurrentUser;
import application.service.UserService;
import com.google.common.collect.Sets;
import domain.user.User;
import domain.user.UserRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestConfig.class)
public abstract class Base {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        Authentication authentication = getAuthentication();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(result -> result.getRequest().setUserPrincipal(authentication))
                .build();

        RestAssuredMockMvc.mockMvc(mvc);
    }

    private Authentication getAuthentication() {
        String email = "someName@mail.com";
        Optional<User> possibleUser = userRepository.findByEmail(email);
        User user;
        if (possibleUser.isPresent()) {
            user = possibleUser.get();
        } else {
            user = userService.createWithRoleArchitect(email, "password");
        }
        CurrentUser currentUser = new CurrentUser(user.getId().toString(), user.getEmail(), "pass", Sets.newHashSet(new String[]{"ARCHITECT"}));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(currentUser);
        return authentication;
    }
}
