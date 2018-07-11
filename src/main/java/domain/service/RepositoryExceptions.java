package domain.service;

public class RepositoryExceptions {

    static class RepositoryException extends RuntimeException {
        public RepositoryException(String message) {
            super(message);
        }

        public RepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ProjectRepositoryException extends RepositoryException {
        public ProjectRepositoryException(String message) {
            super(message);
        }

        public ProjectRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class UserRepositoryException extends RepositoryException {
        public UserRepositoryException(String message) {
            super(message);
        }

        public UserRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InspirationRepositoryException extends RepositoryException {
        public InspirationRepositoryException(String message) {
            super(message);
        }

        public InspirationRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
