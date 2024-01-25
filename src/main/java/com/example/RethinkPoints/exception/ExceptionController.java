package com.example.RethinkPoints.exception;

import com.example.RethinkPoints.dto.ErrorDTO;
import com.example.RethinkPoints.utils.AppUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Component
@Slf4j
public class ExceptionController {
    /*---------------------------------GENERIC---------------------------------*/
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleGenericException(Exception ex) {
        log.error("handleGenericException() - 'Erro Captado': {}", (Object) ex.getStackTrace());
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    /*-------------------------INTERNAL SERVER ERROR---------------------------*/
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleNullPointerException(NullPointerException ex) {
        String[] parts = Arrays.toString(ex.getStackTrace()).split(",");
        return ErrorDTO.builder().errorMessage("NullPointer at: " + parts[0].trim() + "  /  " + parts[1].trim() ).build() ;
    }
    /*------------------------------BAD REQUEST--------------------------------*/
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(DateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleDateTimeException(DateTimeException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleHttpMessageNotReadableException(Exception ex) {
        return ErrorDTO.builder().errorMessage(AppUtils.trySubstringProblem(ex.getMessage())).build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCpfException.class)
    public ErrorDTO handleInvalidCpfException(Exception ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    /*---------------------------------NOT FOUND---------------------------------*/
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorDTO handleNoSuchElementException(Exception ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorDTO handleEntityNotFoundException(Exception ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    /*---------------------------------CONFLICT---------------------------------*/
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    public ErrorDTO handleEntityExistsException(Exception ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(BalanceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleBalanceException(BalanceException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    /*---------------------------------UNAUTHORIZED---------------------------------*/
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleAccessDeniedException(AccessDeniedException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(JwtFilterExecption.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleJwtFilterException(JwtFilterExecption ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleExpiredJwtException(ExpiredJwtException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleMalformedJwtException(MalformedJwtException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleSignatureException(SignatureException ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }
}