package tr.com.paydaytrade.authenticationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import tr.com.paydaytrade.authenticationservice.domain.dto.ErrorMessageDto;

import java.util.Date;

@RestControllerAdvice
class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ErrorMessageDto defaultErrorHandler(WebRequest request, Exception e) throws Exception {
        return new ErrorMessageDto(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
    }
}