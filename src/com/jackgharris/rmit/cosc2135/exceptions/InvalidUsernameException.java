package com.jackgharris.rmit.cosc2135.exceptions;

public class InvalidUsernameException extends Exception{
    public InvalidUsernameException(){
        super("Invalid username provided!\nusernames must be not blank and unique to one user");
    }
}
