package application.rest;

import application.UserAuthenticationService;
import application.dto.AuthDto;
import application.dto.CurrentUser;
import application.dto.Token;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/auth/",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class AuthController {

    @NonNull
    UserAuthenticationService authentication;

    @PostMapping("register")
    ResponseEntity register(@RequestBody AuthDto authDto) {
        authentication.register(authDto.getUsername(), authDto.getPassword());
        return handleLogin(authDto.getUsername(), authDto.getPassword());
    }

    @PostMapping("login")
    ResponseEntity login(@RequestBody AuthDto authDto) {
        return handleLogin(authDto.getUsername(), authDto.getPassword());
    }

    @GetMapping("logout")
    boolean logout(@AuthenticationPrincipal final CurrentUser user) {
        authentication.logout(user);
        return true;
    }

    private ResponseEntity handleLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return authentication.login(username, password)
                .map(URI::create)
                .map((uri) -> ResponseEntity.created(uri).body(new Token(uri.toString())))
                .orElse(ResponseEntity.badRequest().build());
    }

}

