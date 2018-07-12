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
}