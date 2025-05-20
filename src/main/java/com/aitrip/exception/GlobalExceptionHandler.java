package com.aitrip.exception;

import com.aitrip.exception.external.amadeus.*;
import com.aitrip.exception.external.phone.InvalidPhoneNumberException;
import com.aitrip.exception.external.phone.PhoneValidationServiceException;
import com.aitrip.exception.plan.NullPlanCreateDTOException;
import com.aitrip.exception.plan.NullPlanNameException;
import com.aitrip.exception.prompt.EmptySystemPromptException;
import com.aitrip.exception.prompt.EmptyUserPromptException;
import com.aitrip.exception.prompt.NullPromptException;
import com.aitrip.exception.prompt.PromptNotFoundException;
import com.aitrip.exception.prompt.PromptValidationException;
import com.amadeus.exceptions.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
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
     * Handles specific AmadeusTokenNotFoundException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(AmadeusTokenNotFoundException.class)
    public ResponseEntity<Object> handleAmadeusTokenNotFoundException(
            AmadeusTokenNotFoundException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles specific InvalidPhoneNumberException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<Object> handleInvalidPhoneNumberException(
            InvalidPhoneNumberException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles specific PhoneValidationServiceException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(PhoneValidationServiceException.class)
    public ResponseEntity<Object> handlePhoneValidationServiceException(
            PhoneValidationServiceException ex, WebRequest request) {
        return createErrorResponse(ex, request, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Handles NoFlightsAvailableException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(NoFlightsAvailableException.class)
    public ResponseEntity<Object> handleNoFlightsAvailableException(
            NoFlightsAvailableException ex, WebRequest request) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        additionalInfo.put("origin", ex.getOrigin());
        additionalInfo.put("destination", ex.getDestination());
        additionalInfo.put("startDate", ex.getStartDate());
        additionalInfo.put("endDate", ex.getEndDate());

        return createTravelErrorResponse(ex, request, HttpStatus.NOT_FOUND, additionalInfo);
    }

    /**
     * Handles FlightSearchFailedException.
     *
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(FlightSearchFailedException.class)
    public ResponseEntity<Object> handleFlightSearchFailedException(
            FlightSearchFailedException ex, WebRequest request) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        additionalInfo.put("hint", "The flight search service is currently unavailable. Please try again later.");

        return createTravelErrorResponse(ex, request, HttpStatus.BAD_GATEWAY, additionalInfo);
    }

    /**
     * Handles HotelSearchFailedException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HotelSearchFailedException.class)
    public ResponseEntity<Object> handleHotelSearchFailedException(
            HotelSearchFailedException ex, WebRequest request) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        additionalInfo.put("hint", "No hotels found for the specified criteria. Try different dates or location.");

        return createTravelErrorResponse(ex, request, HttpStatus.NOT_FOUND, additionalInfo);
    }

    /**
     * Handles InvalidAmadeusEnvironmentException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(InvalidAmadeusEnvironmentException.class)
    public ResponseEntity<Object> handleInvalidAmadeusEnvironmentException(
            InvalidAmadeusEnvironmentException ex, WebRequest request) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        additionalInfo.put("invalidEnvironment", ex.getEnvironment());
        additionalInfo.put("validEnvironments", ex.getValidEnvironments());

        return createAmadeusConfigErrorResponse(ex, request, HttpStatus.BAD_REQUEST, additionalInfo);
    }

    /**
     * Handles MissingAmadeusConfigurationException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MissingAmadeusConfigurationException.class)
    public ResponseEntity<Object> handleMissingAmadeusConfigurationException(
            MissingAmadeusConfigurationException ex, WebRequest request) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        additionalInfo.put("environment", ex.getEnvironment());
        additionalInfo.put("missingProperty", ex.getMissingProperty());

        return createAmadeusConfigErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, additionalInfo);
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

    /**
     * Creates a standardized error response for travel-related exceptions.
     * 
     * @param ex The exception
     * @param request The web request
     * @param status The HTTP status
     * @param additionalInfo Additional information to include in the response
     * @return ResponseEntity with error details
     */
    private ResponseEntity<Object> createTravelErrorResponse(Exception ex, WebRequest request, 
                                                           HttpStatus status, Map<String, Object> additionalInfo) {
        // Get the basic response body from createErrorResponse
        ResponseEntity<Object> response = createErrorResponse(ex, request, status);

        // Extract the body and add additional information
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        if (body != null) {
            // Add all additional information
            body.putAll(additionalInfo);

            // Add common options for travel-related exceptions
            body.put("options", List.of(
                "Search with flexible dates (±3 days)",
                "Change destination",
                "Try different dates"
            ));
        }

        return new ResponseEntity<>(body, status);
    }

    /**
     * Handles HotelOfferSearchFailedException.
     * 
     * @param ex The exception
     * @param request The web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HotelOfferSearchFailedException.class)
    public ResponseEntity<Object> handleHotelOfferSearchFailedException(
            HotelOfferSearchFailedException ex, WebRequest request) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        additionalInfo.put("hint", "Try different dates, increase your search radius, or check for available hotels in another city.");

        return createTravelErrorResponse(ex, request, HttpStatus.NOT_FOUND, additionalInfo);
    }


    /**
     * Creates a standardized error response for Amadeus configuration exceptions.
     * 
     * @param ex The exception
     * @param request The web request
     * @param status The HTTP status
     * @param additionalInfo Additional information to include in the response
     * @return ResponseEntity with error details
     */
    private ResponseEntity<Object> createAmadeusConfigErrorResponse(Exception ex, WebRequest request, 
                                                                  HttpStatus status, Map<String, Object> additionalInfo) {
        ResponseEntity<Object> response = createErrorResponse(ex, request, status);

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        if (body != null) {
            body.putAll(additionalInfo);

            if (status == HttpStatus.BAD_REQUEST) {
                body.put("suggestions", List.of(
                    "Use 'test' or 'production' as environment values",
                    "Check your environment parameter spelling"
                ));
            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
                body.put("suggestions", List.of(
                    "Check your application.yaml configuration",
                    "Ensure all required environment variables are set",
                    "Verify Amadeus API credentials"
                ));
            }
        }

        return new ResponseEntity<>(body, status);
    }
}
