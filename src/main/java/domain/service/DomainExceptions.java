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

    public static class ProjectRepositoryException extends DomainException {
        public ProjectRepositoryException(String message) {
            super(message);
        }

        public ProjectRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class UserRepositoryException extends DomainException {
        public UserRepositoryException(String message) {
            super(message);
        }

        public UserRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InspirationRepositoryException extends DomainException {
        public InspirationRepositoryException(String message) {
            super(message);
        }

        public InspirationRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class MissingInspirationException extends DomainException {
        public MissingInspirationException(String inspirationId) {
            super(inspirationId);
        }
    }

}
