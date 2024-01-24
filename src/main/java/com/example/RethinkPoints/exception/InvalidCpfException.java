package com.example.RethinkPoints.exception;

public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException() {
        super("CPF Invalido");
    }
}
