//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.core;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.controllers.*;
import com.jackgharris.rmit.cosc2135.exceptions.InvalidViewException;
import com.jackgharris.rmit.cosc2135.models.*;

//**** START CLASS ****\\
public class WhatsAppConsoleEdition {

    //Private Class Variables
    //-------------------------------------------------------------------------------------------
    //Declare the active controller string variable, this is used to keep track of what controller is currently active
    private String activeController;
    //Declare our user variable, this is used to keep track of what user is currently logged in
    private User user;
    //declare our controllers list
    private final CustomArray controllers;

    //**** CONSTRUCTOR ****\\
    public WhatsAppConsoleEdition(){
        //Models are done this way so we only have single instances of a model in the event two controllers both need the same model
        //create our single instances of our models these are parsed to the controllers if needed in there constructors
        UserModel userModel = new UserModel();
        MessageModel messageModelModel = new MessageModel();

        //Declare our list of controller instances and add them to the controllers array
        this.controllers = new CustomArray(Controller.class);
        this.controllers.add(new LoginController(this,userModel),"login");
        this.controllers.add(new DashboardController(this,userModel,messageModelModel), "dashboard");

        //set the active controller for this application to the login screen. (default start controller)
        this.setActiveController("login");
        this.updateView(null);
    }

    //**** SET ACTIVE CONTROLLER ****\\
    //sets the active controller based on a string
    public void setActiveController(String key){
        if(this.controllers.arrayKeyExists(key)) {
            this.activeController = key;
        }else{
            this.activeController = "login";
            CustomArray response = new CustomArray(String.class);
            response.add("Error 500! Failed load controller "+key+"\nredirecting you back to the login","error");
            response.add("welcome","redirect");
            this.updateView(response);
        }
    }


    //**** UPDATE VIEW ****\\
    //calls the update method of the view that is currently active with in the program
    public void updateView(CustomArray response){
        //open our try catch to update the view, we will be trying to catch a unknown view error
        try {
            //process our update view and parse it our response
            ((Controller) this.controllers.getValue(this.activeController)).updateView(response);

            //if we catch an error proceed
        } catch (InvalidViewException e) {

            //save the view we attempted to load
            String targetView = ((Controller) this.controllers.getValue(this.activeController)).getCurrentView();
            //add the error to the response to be displayed to the user
            response.add("Error 500! Were sorry the requested sub view "+targetView+"\ncould not be found in controller "+this.activeController,"error");
            //now add a redirect to the response
            response.add("welcome","redirect");
            //lastly we set our active controller back to the welcome view
            this.activeController = "login";
            //finally we recall this update function
            this.updateView(response);
        }
    }

    //**** SET CURRENT USER ****\\
    //this method sets the current user to the parsed user
    public void setCurrentUser(User user){
        this.user = user;
    }

    //**** GET CURRENT USER ****\\
    //this method returns the current user from the this.user private variable
    public User getCurrentUser(){
        return this.user;
    }

    //**** MAIN STATIC VOID ****\\
    //tells java to start the program here and creates a new instance of the WhatsAppConsoleEdition main class
    public static void main(String[] args){
        new WhatsAppConsoleEdition();
    }

}
