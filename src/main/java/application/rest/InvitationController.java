package application.rest;

import application.dto.InvitationDto;
import application.service.InvitationService;
import com.google.common.net.HttpHeaders;
import domain.invitation.Invitation;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.PERMANENT_REDIRECT;

@RestController
@RequestMapping(
        path = InvitationController.INVITATION
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class InvitationController {


    public static final String INVITATION = "/invitations/";

    InvitationService invitationService;

    @GetMapping("{token}")
    public ResponseEntity<InvitationDto> invitation(@PathVariable String token) {
        Invitation invitation = invitationService.resolve(token);
        return ResponseEntity.status(PERMANENT_REDIRECT).header(HttpHeaders.LOCATION, invitation.getRedirection())
                .build();

    }

}
