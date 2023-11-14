package com.example.hogwartsPoints.exceptions;

public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException() {
        super("CPF Invalido");
    }
}
