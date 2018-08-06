package application.service;

public class RepositoryExceptions {

    public static class RepositoryException extends RuntimeException {
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
    }

    public static class QuestionnaireTemplateRepositoryException extends RepositoryException {
        public QuestionnaireTemplateRepositoryException(String message) {
            super(message);
        }
    }
}
