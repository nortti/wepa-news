package wepa.news.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// We allow any known exceptions that may occur and handle them here by
// responding appropriately, possibly with helpful a helpful message.
// Only happy paths need to be considered in controllers/services.
// Custom exceptions would be needed in the future if this was extended
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        // This gets thrown when objects marked as @RequestBody @Valid don't pass the annotation-based
        // validation. Responds with the annotatated message for each failed field.
        //i.e. @NotBlank(message = "Name must not be blank")
        // --> add "Name must not be blank" is added to response
        return handleExceptionInternal(ex, getDefaultMessages(ex.getBindingResult()), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        // See above, except without @RequestBody (i.e. newscontroller post)
        return handleExceptionInternal(ex, getDefaultMessages(ex.getBindingResult()), headers, status, request);
    }

    private List<String> getDefaultMessages(BindingResult bindingResult) {
        return bindingResult
                .getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .sorted()
                .collect(Collectors.toList());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<List<String>> handleEmptyResultDataAccessException() {
        // When deleting an object that doesn't exist anymore
        return badRequestResponse("Someone beat you to it");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<String>> handleEntityNotFoundException() {
        return badRequestResponse("You tried to modify an object that doesn't exist anymore");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException exception) {
        String message;
        switch (exception.getErrorCode()) {
            case 23505:
                message = "Such an object already exists";
                break;
            case 23503:
                message = "Object is used in a news item (go remove it first).";
                break;
            default:
                message = "Something went wrong.";
        }
        return badRequestResponse(message);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<List<String>> handleIOException() {
        return badRequestResponse("Something went wrong with that image.");
    }

    private ResponseEntity<List<String>> badRequestResponse(String message) {
        // We return it as a list since MethodArgumentNotValid response can have multiple messages,
        // and it's easier for the receiving end to only handle one format
        return new ResponseEntity<>(Collections.singletonList(message), HttpStatus.BAD_REQUEST);
    }
}
