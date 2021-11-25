//**** SET PACKAGE ****\\
package exceptions;

//**** START CLASSS ****\\
public class InvalidUsernameException extends Exception{
    //Invalid username exception is thrown by the UserModel if the validation fails
    public InvalidUsernameException(){
        //set the message that is going to be thrown from this exception.
        super("Invalid username provided!\nusernames must be not blank and unique to one user");
    }
}
