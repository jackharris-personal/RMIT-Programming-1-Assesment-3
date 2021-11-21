//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.views;

//**** IMPORT PACKAGES ****\\
import com.jackgharris.rmit.cosc2135.assignment3.core.CustomArray;
import com.jackgharris.rmit.cosc2135.assignment3.core.TextColors;
import java.io.IOException;

//**** START CLASS ****\\
public class ErrorView extends View{

    //404 view (View not found), accepts a response and returns a request, displayed on a fatal view error
    public CustomArray viewNotFound(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the title of this view to the user
        this.showTitle("Error 404: unknown view!");

        //show the response error message back to the user
        this.showAlert((String)response.getValue("error"), TextColors.textRed);

        //show the menu
        System.out.println("1) reload view");
        System.out.println("2) back to login");
        System.out.println("\nif problems persist please contact support");

        //reparse the target view and controller back to the system, these were the attempted call you made before the error
        //was thrown
        request.add(response.getValue("targetView"),"targetView");
        request.add(response.getValue("targetController"),"targetController");

        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in

        //return the request
        return request;
    }

    //Controller not found view (error 500 fatel error) accepts a response and returns a request, is called by the main  app if a invalid controller is set
    public CustomArray controllerNotFound(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the title of this view to the user
        this.showTitle("Error 500: fatal error");

        //show the response error message back to the user
        this.showAlert((String)response.getValue("error"), TextColors.textRed);


        //get the user input
        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in

        //return the request
        return request;
    }
}
