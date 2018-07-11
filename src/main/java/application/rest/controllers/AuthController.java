package application.rest.controllers;

import application.UserAuthenticationService;
import application.rest.controllers.dto.AuthDto;
import application.rest.controllers.dto.Token;
import application.rest.controllers.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/auth/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class AuthController {

    @NonNull
    UserAuthenticationService authentication;

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthDto authDto = objectMapper.readValue("{\"username\":\"zdenek1\", \"password\":\"pass\"}", AuthDto.class);
        System.out.println(authDto);

    }

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
    boolean logout(@AuthenticationPrincipal final UserDto user) {
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

