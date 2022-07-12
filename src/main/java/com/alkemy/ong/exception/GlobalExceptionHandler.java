package com.alkemy.ong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ResponseStatus(HttpStatus.CONFLICT)
   @ExceptionHandler({
      EmailExistsException.class
   })
   @ResponseBody
   protected ExceptionDetails conflictHandler(Exception exception, HttpServletRequest request) {
      return new ExceptionDetails(LocalDateTime.now(), exception, request);
   }

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler({
           UserNotFoundException.class,
           OrgNotFoundException.class,
           MemberNotFoundException.class,
           ActivityNotFoundException.class,
      EntityNotFoundException.class
   })
   @ResponseBody
   protected ExceptionDetails notFoundHandler(Exception exception, HttpServletRequest request) {
      return new ExceptionDetails(LocalDateTime.now(), exception, request);
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler({
      NullPointerException.class
   })
   @ResponseBody
   protected ExceptionDetails internalErrorHandler(Exception exception, HttpServletRequest request) {
      return new ExceptionDetails(LocalDateTime.now(), exception, request);
   }
}
