//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.exceptions;

//**** START CLASSS ****\\
public class InvalidPasswordException extends Exception{
    //invalid password exception extends the exception and is thrown by the UserModel if the password check fails
    public InvalidPasswordException(){
        //set the exception message that needs to be thrown
        super("Invalid password provided!\npasswords must not be blank");
    }
}
