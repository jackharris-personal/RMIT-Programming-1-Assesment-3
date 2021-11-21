package com.jackgharris.rmit.cosc2135.assignment3.exceptions;

public class InvalidViewException extends Exception{
    public InvalidViewException(){
        super("Unknown view method called");
    }
}
