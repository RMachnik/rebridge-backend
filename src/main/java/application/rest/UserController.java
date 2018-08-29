package application.rest;

import application.dto.CurrentUser;
import application.dto.LogoutSuccessDto;
import application.dto.ProfileDto;
import application.dto.UpdateProfileDto;
import application.service.UserAuthenticationService;
import application.service.UserService;
import domain.user.User;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static application.dto.ProfileDto.create;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;

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
    UserService userService;

    @GetMapping("current")
    ResponseEntity<ProfileDto> current(@AuthenticationPrincipal final CurrentUser user) {
        return ok(create(userService.findByEmail(user.getEmail())));
    }

    @PutMapping("current")
    ResponseEntity<ProfileDto> update(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody UpdateProfileDto updateProfileDto) {
        User user = userService.update(currentUser, updateProfileDto);
        return ok(create(user));
    }

    @DeleteMapping("logout")
    ResponseEntity logout(@AuthenticationPrincipal final CurrentUser user) {
        authenticationService.logout(user);
        return ok(new LogoutSuccessDto(true));
    }

}
