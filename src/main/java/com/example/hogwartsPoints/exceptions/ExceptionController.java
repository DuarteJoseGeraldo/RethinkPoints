package com.example.hogwartsPoints.exceptions;

import com.example.hogwartsPoints.DTOs.ErrorDTO;
import com.example.hogwartsPoints.utils.AppUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleGenericException(Exception ex) {
        return ErrorDTO.builder().errorMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleHttpMessageNotReadableException(Exception ex) {
        return ErrorDTO.builder().errorMessage(AppUtils.trySubstringProblem(ex.getMessage())).build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorDTO handleNoSuchElementException(Exception exception) {
        return ErrorDTO.builder().errorMessage(exception.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorDTO handleEntityNotFoundException(Exception exception) {
        return ErrorDTO.builder().errorMessage(exception.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    public ErrorDTO handleEntityExistsException(Exception exception) {
        return ErrorDTO.builder().errorMessage(exception.getMessage()).build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCpfException.class)
    public ErrorDTO handleInvalidCpfException(Exception exception) {
        return ErrorDTO.builder().errorMessage(exception.getMessage()).build();
    }
}
