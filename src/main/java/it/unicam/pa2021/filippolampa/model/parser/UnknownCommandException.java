package it.unicam.pa2021.filippolampa.model.parser;

public class UnknownCommandException extends Exception{

    public UnknownCommandException(String errorMessage){
        super(errorMessage);
    }

}
