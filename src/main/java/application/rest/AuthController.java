package application.rest;

import application.dto.AuthDto;
import application.dto.CurrentUser;
import application.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    UserAuthenticationService authenticationService;

    @PostMapping("register")
    ResponseEntity<CurrentUser> register(@RequestBody AuthDto authDto) {
        authenticationService.register(authDto.getEmail(), authDto.getPassword());
        return handleLogin(authDto.getEmail(), authDto.getPassword());
    }

    @PostMapping("login")
    ResponseEntity<CurrentUser> login(@RequestBody AuthDto authDto) {
        return handleLogin(authDto.getEmail(), authDto.getPassword());
    }

    @GetMapping("login/{token}")
    ResponseEntity<CurrentUser> login(@PathVariable String token) {
        return authenticationService.check(token)
                .map((currentUser) ->
                        ResponseEntity.created(URI.create(currentUser.getToken()))
                                .body(currentUser))
                .orElse(ResponseEntity.badRequest().build());
    }

    private ResponseEntity<CurrentUser> handleLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
        return authenticationService.login(email, password)
                .map((currentUser) ->
                        ResponseEntity.created(URI.create(currentUser.getToken()))
                                .body(currentUser))
                .orElse(ResponseEntity.badRequest().build());
    }

}

