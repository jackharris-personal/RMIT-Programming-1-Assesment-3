package com.jackgharris.rmit.cosc2135.exceptions;

public class InvalidPasswordException extends Exception{

    public InvalidPasswordException(){
        super("Invalid password provided!\npasswords must not be blank");
    }
}
