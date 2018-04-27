package com.processo.seletivo.core.exception;

import com.processo.seletivo.core.controller.JsonResponse;
import com.processo.seletivo.core.controller.JsonResponseFactory;
import com.processo.seletivo.core.controller.JsonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    protected JsonResponseService jsonResponseService;

    @Autowired
    protected JsonResponseFactory jsonResponseFactory;

    private ResponseEntity<?> formatDefaultResponse(Exception e){
        return this.formatDefaultResponse(e, jsonResponseService.getHttpStatus());
    }
    private ResponseEntity<?> formatDefaultResponse(Exception e, HttpStatus status){
        return this.formatDefaultResponse("", status);
    }


    private ResponseEntity<?> formatDefaultResponse(String messageKey, HttpStatus status){
        jsonResponseService.clearMessages();
        //jsonResponseService.addError(messageKey);
        JsonResponse response = jsonResponseFactory.create(null, jsonResponseService.getMessageList());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(RuntimeException e) {
        return this.formatDefaultResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({CustomPermissionException.class, AccessDeniedException.class})
    public ResponseEntity<?> handlePermissionException(RuntimeException e) {
        return this.formatDefaultResponse(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException e) {
        return this.formatDefaultResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomDuplicatedException.class})
    public ResponseEntity<?> handleDuplicatedException(RuntimeException e) {
        return this.formatDefaultResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomDependencyException.class)
    public ResponseEntity<?> handleDependencyException(RuntimeException e) {
        return this.formatDefaultResponse(e, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler({CustomInternalException.class, RuntimeException.class})
    public ResponseEntity<?> handleInternalException(RuntimeException e) {
        return this.formatDefaultResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
