package application.rest.controllers;

import application.UserAuthenticationService;
import domain.User;
import dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class SecuredUsersController {
    @NonNull
    UserAuthenticationService authentication;

    @GetMapping("/current")
    UserDto getCurrent(@AuthenticationPrincipal final UserDto user) {
        return user;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final UserDto user) {
        authentication.logout(User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build());
        return true;
    }
}
