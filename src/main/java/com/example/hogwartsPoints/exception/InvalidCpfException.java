package com.example.hogwartsPoints.exception;

public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException() {
        super("CPF Invalido");
    }
}
