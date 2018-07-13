package application.service;

public class ServiceExceptions {
    public static class ServiceException extends RuntimeException {
        public ServiceException(String message) {
            super(message);
        }
    }
}
