//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.exceptions;

//**** START CLASSS ****\\
public class InvalidFullnameException extends Exception{
    //Invalid full name exception extends the exception class and is thrown by the registration model if the full name check fails
    public InvalidFullnameException(){
        //set the main message for the exception to throw
        super("Invalid fullname provided!\nFullnames must be not blank");
    }
}
