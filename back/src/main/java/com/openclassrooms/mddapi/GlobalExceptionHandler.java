package com.openclassrooms.mddapi;

import com.openclassrooms.mddapi.exception.*;
import com.openclassrooms.mddapi.model.ErrorEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorEntity> userNotFoundHandler(UserNotFoundException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ErrorEntity> userNotFoundHandler(UserNameNotFoundException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ErrorEntity> topicNotFoundHandler(TopicNotFoundException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ErrorEntity> subjectNotFoundHandler(SubjectNotFoundException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorEntity> subscriptionNotFoundHandler(SubscriptionNotFoundException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorEntity> badRequestHandler(BadRequestException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorEntity> unauthorizedHandler(UnauthorizedException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorEntity> forbiddenHandler(ForbiddenException exception) {
        ErrorEntity error = ErrorEntity.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .httpStatus(HttpStatus.FORBIDDEN.value())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }
}
