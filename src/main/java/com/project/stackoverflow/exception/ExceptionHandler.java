package com.project.stackoverflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class AnswerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AnswerException.class)
    public final ResponseEntity<String> handleAnswerException(AnswerException exception,
                                                               WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<String>(exception.getError().name(), status);
    }
}


@ControllerAdvice
class QuestionExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QuestionException.class)
    public final ResponseEntity<String> handleQuestionException(QuestionException exception,
                                                              WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<String>(exception.getError().name(), status);
    }
}

@ControllerAdvice
class CommunityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommunityException.class)
    public final ResponseEntity<String> handleCommunityException(CommunityException exception,
                                                                WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<String>(exception.getError().name(), status);
    }
}

@ControllerAdvice
class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<String> handleUserException(UserException exception,
                                                                 WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<String>(exception.getError().name(), status);
    }
}

@ControllerAdvice
class TagExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TagException.class)
    public final ResponseEntity<String> handleTagException(TagException exception,
                                                            WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<String>(exception.getError().name(), status);
    }
}
