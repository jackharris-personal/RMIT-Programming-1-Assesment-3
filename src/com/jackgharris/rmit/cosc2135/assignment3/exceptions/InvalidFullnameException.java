package com.jackgharris.rmit.cosc2135.assignment3.exceptions;

public class InvalidFullnameException extends Exception{
    public InvalidFullnameException(){
        super("Invalid fullname provided!\nFullnames must be not blank");
    }
}
