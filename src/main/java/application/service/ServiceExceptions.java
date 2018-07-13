package application.service;

public class ServiceExceptions {
    static class ServiceException extends RuntimeException {
        public ServiceException(String message) {
            super(message);
        }
    }
}
