//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.exceptions;

//**** START CLASSS ****\\
public class InvalidViewException extends Exception{
    //invalid view exception is thrown by the update view class if a redirect calls a invalid view
    public InvalidViewException(){
        //set the message to be caught and displayed
        super("Unknown view method called");
    }
}
