package domain;

public class DomainExceptions {

    public static class DomainException extends RuntimeException {

        public DomainException(String message) {
            super(message);
        }
    }

    public static class UserActionNotAllowed extends DomainException {

        public UserActionNotAllowed(String message) {
            super(message);
        }
    }

    public static class MissingInspirationException extends DomainException {

        public MissingInspirationException(String inspirationId) {
            super(inspirationId);
        }
    }

    public static class MissingCommentException extends DomainException {

        public MissingCommentException(String message) {
            super(message);
        }
    }

    public static class InvalidPassword extends DomainException {

        public InvalidPassword(String message) {
            super(message);
        }
    }

    public static class EmailValidation extends DomainException {

        public EmailValidation(String message) {
            super(message);
        }
    }

    public static class MissingQuestionnaireTemplate extends DomainException {

        public MissingQuestionnaireTemplate(String message) {
            super(message);
        }
    }

    public static class MissingQuestion extends DomainException {
        public MissingQuestion(String message) {
            super(message);
        }
    }

    public static class InvalidPostalCode extends DomainException {
        public InvalidPostalCode(String message) {
            super(message);
        }
    }

    public static class InvalidInvitation extends DomainException {
        public InvalidInvitation(String message) {
            super(message);
        }
    }

    public static class MissingEvent extends DomainException {

        public MissingEvent(String message) {
            super(message);
        }
    }

    public static class MissingDocumentation extends DomainException {
        public MissingDocumentation(String message) {
            super(message);
        }
    }

    public static class MissingRoom extends DomainException {
        public MissingRoom(String message) {
            super(message);
        }
    }

    public static class MissingCategory extends DomainException {
        public MissingCategory(String message) {
            super(message);
        }
    }
}
