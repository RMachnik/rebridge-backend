package application.rest;

import application.service.ServiceExceptions.ServiceException;
import com.fasterxml.jackson.annotation.JsonFormat;
import domain.DomainExceptions.DomainException;
import domain.RepositoryExceptions.RepositoryException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error(request.getContextPath(), ex);
        return buildResponseEntity(new ApiError(status, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({DomainException.class})
    protected ResponseEntity<Object> domainException(DomainException ex, WebRequest request) {
        log.error(request.getContextPath(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({RepositoryException.class})
    protected ResponseEntity<Object> notFoundResource(RepositoryException ex, WebRequest request) {
        log.error(request.getContextPath(), ex);
        ApiError apiError = new ApiError(NOT_FOUND, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<Object> serviceException(ServiceException ex, WebRequest request) {
        log.error(request.getContextPath(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @Value
    static class ApiError {

        HttpStatus status;
        int httpCode;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        LocalDateTime timestamp;
        String message;

        public ApiError(HttpStatus status, String message) {
            this.httpCode = status.value();
            this.status = status;
            this.timestamp = LocalDateTime.now();
            this.message = message;
        }
    }

}
