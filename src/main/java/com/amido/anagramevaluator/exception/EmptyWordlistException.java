package com.amido.anagramevaluator.exception;

public class EmptyWordlistException extends RuntimeException {

    public EmptyWordlistException(final String message){
        super(message);
    }

}
