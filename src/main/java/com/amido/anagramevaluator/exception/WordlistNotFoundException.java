package com.amido.anagramevaluator.exception;

public class WordlistNotFoundException extends RuntimeException {

    public WordlistNotFoundException(final String message){
        super(message);
    }

}
