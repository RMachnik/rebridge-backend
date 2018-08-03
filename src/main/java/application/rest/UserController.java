package application.rest;

import application.dto.CurrentUser;
import application.dto.LogoutSuccessDto;
import application.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/users/",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class UserController {

    UserAuthenticationService authenticationService;

    @GetMapping("current")
    ResponseEntity current(@AuthenticationPrincipal final CurrentUser user) {
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("logout")
    ResponseEntity logout(@AuthenticationPrincipal final CurrentUser user) {
        authenticationService.logout(user);
        return ResponseEntity.ok(new LogoutSuccessDto(true));
    }

}
