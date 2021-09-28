package it.unicam.pa2021.filippolampa.model.parser;

public class IllegalPathException extends Exception{

    public IllegalPathException(String errorMessage){
        super(errorMessage);
    }
}