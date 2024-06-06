package com.dws.challenge.exception;

public class InsufficientBalanceManagerException extends RuntimeException{

    public InsufficientBalanceManagerException(String format, Object[] parameters) {
        super(String.format(format, parameters));
    }

    public static InsufficientBalanceManagerException to(String format, Object... parameters) {
        return new InsufficientBalanceManagerException(format, parameters);
    }
}
