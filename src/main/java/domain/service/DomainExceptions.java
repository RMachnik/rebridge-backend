package domain.service;

public class DomainExceptions {

    static class DomainException extends RuntimeException {
        public DomainException(String message) {
            super(message);
        }

        public DomainException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class MissingUserException extends DomainException {
        public MissingUserException(String message) {
            super(message);
        }
    }

    public static class MissingProjectException extends DomainException {
        public MissingProjectException(String message) {
            super(message);
        }
    }

    public static class AddingProjectException extends DomainException {
        public AddingProjectException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class UserRepositoryException extends DomainException {
        public UserRepositoryException(String message) {
            super(message);
        }
    }

    public static class AddingInspirationException extends DomainException {
        public AddingInspirationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class MissingInspirationException extends DomainException {
        public MissingInspirationException(String inspirationId) {
            super(inspirationId);
        }
    }

    public static class UnableToCreateComment extends DomainException {
        public UnableToCreateComment(String message, Throwable ex) {
            super(message, ex);
        }
    }
}
