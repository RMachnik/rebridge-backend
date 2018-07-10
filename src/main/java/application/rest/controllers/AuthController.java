package application.rest.controllers;

import application.UserAuthenticationService;
import application.rest.controllers.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/auth/")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class AuthController {

    @NonNull
    UserAuthenticationService authentication;

    @PostMapping("register")
    ResponseEntity register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        authentication.register(username, password);
        return handleLogin(username, password);
    }

    @PostMapping("login")
    ResponseEntity login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return handleLogin(username, password);
    }

    @GetMapping("logout")
    boolean logout(@AuthenticationPrincipal final UserDto user) {
        authentication.logout(user);
        return true;
    }

    private ResponseEntity handleLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return authentication.login(username, password)
                .map(URI::create)
                .map((uri) -> ResponseEntity.created(uri).build())
                .orElse(ResponseEntity.badRequest().build());
    }
}

