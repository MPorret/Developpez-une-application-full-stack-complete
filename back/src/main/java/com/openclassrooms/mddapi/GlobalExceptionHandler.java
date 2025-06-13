package com.openclassrooms.mddapi;

import com.openclassrooms.mddapi.exception.*;
import com.openclassrooms.mddapi.dto.ErrorDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> userNotFoundHandler(UserNotFoundException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ErrorDTO> userNotFoundHandler(UserNameNotFoundException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ErrorDTO> topicNotFoundHandler(TopicNotFoundException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorDTO> postNotFoundHandler(PostNotFoundException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorDTO> subscriptionNotFoundHandler(SubscriptionNotFoundException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> badRequestHandler(BadRequestException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> unauthorizedHandler(UnauthorizedException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorDTO> forbiddenHandler(ForbiddenException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.FORBIDDEN.value())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }
}
