package application.service;

import application.dto.CurrentUser;
import application.dto.UpdateProfileDto;
import application.service.ServiceExceptions.ServiceException;
import domain.DomainExceptions.InvalidPassword;
import domain.RepositoryExceptions.UserRepositoryException;
import domain.invitation.Invitation;
import domain.user.EmailAddress;
import domain.user.Roles;
import domain.user.User;
import domain.user.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static domain.user.Roles.ARCHITECT;
import static domain.user.Roles.INVESTOR;
import static java.lang.String.format;

@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    MailService mailService;
    InvitationService invitationService;

    public User createWithRoleArchitect(String email, String password) {
        return createUserWithRole(email, password, ARCHITECT);
    }

    public User createWithRoleInvestor(String email) {
        return createUserWithRole(email, "changeme", INVESTOR);
    }

    private User createUserWithRole(String email, String password, Roles role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ServiceException(format("email %s already exists, try different name", email));
        }

        User user = User.createUser(email, password, role);
        User createdUser = userRepository.save(user)
                .getOrElseThrow(
                        ex -> new UserRepositoryException(format("problem with adding %s", email, ex))
                );

        mailService.sendWelcomeEmail(new EmailAddress(user.getEmail()));
        return createdUser;
    }

    public User findById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserRepositoryException(format("user with id %s is missing", userId)));
    }

    public User update(User user) {
        return userRepository.save(user)
                .getOrElseThrow(() -> new UserRepositoryException(format("problem with updating user %s", user.getId())));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserRepositoryException(format("user %s not found", email)));
    }

    public Optional<User> tryFindUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User login(String email, String password) {
        User user = findByEmail(email);
        if (!user.isPasswordValid(password)) {
            throw new InvalidPassword(format("password doesn't match %s", email));
        }
        return user;
    }

    public void sendInvitation(User user) {
        Invitation invitation = invitationService.create(user.getEmailAddress());
        mailService.sendInvitation(invitation);
    }

    public User update(CurrentUser currentUser, UpdateProfileDto updateProfileDto) {
        User user = findByEmail(currentUser.getEmail());
        User updated = user.update(updateProfileDto);
        userRepository.save(updated);
        return updated;
    }
}
