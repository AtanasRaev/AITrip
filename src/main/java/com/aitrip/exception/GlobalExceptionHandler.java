package com.aitrip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Handles all custom exceptions and provides appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles PromptValidationException and its subclasses.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(PromptValidationException.class)
    public ResponseEntity<Object> handlePromptValidationException(
            PromptValidationException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific NullPromptException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(NullPromptException.class)
    public ResponseEntity<Object> handleNullPromptException(
            NullPromptException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific EmptyUserPromptException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(EmptyUserPromptException.class)
    public ResponseEntity<Object> handleEmptyUserPromptException(
            EmptyUserPromptException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific EmptySystemPromptException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(EmptySystemPromptException.class)
    public ResponseEntity<Object> handleEmptySystemPromptException(
            EmptySystemPromptException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific NullPlanCreateDTOException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(NullPlanCreateDTOException.class)
    public ResponseEntity<Object> handleNullPlanCreateDTOException(
            NullPlanCreateDTOException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific NullPlanNameException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(NullPlanNameException.class)
    public ResponseEntity<Object> handleNullPlanNameException(
            NullPlanNameException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific PromptNotFoundException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(PromptNotFoundException.class)
    public ResponseEntity<Object> handlePromptNotFoundException(
            PromptNotFoundException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a standardized error response.
     * 
     * @param ex The exception
     * @param request The web request
     * @param status The HTTP status
     * @return ResponseEntity with error details
     */
    private ResponseEntity<Object> createErrorResponse(Exception ex, WebRequest request, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, status);
    }
}
