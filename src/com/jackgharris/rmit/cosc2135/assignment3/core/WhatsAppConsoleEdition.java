//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.core;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.assignment3.controllers.*;
import com.jackgharris.rmit.cosc2135.assignment3.exceptions.InvalidViewException;
import com.jackgharris.rmit.cosc2135.assignment3.models.MessageModel;
import com.jackgharris.rmit.cosc2135.assignment3.models.Model;
import com.jackgharris.rmit.cosc2135.assignment3.models.User;
import com.jackgharris.rmit.cosc2135.assignment3.models.UserModel;


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
    //declare our models list
    private final CustomArray models;


    //**** CONSTRUCTOR ****\\
    public WhatsAppConsoleEdition(){

        //Models are created in an array and then we can parse the whole array or a number of model from it to the controllers to use.
        this.models = new CustomArray(Model.class);
        //Create and add our user model to the array
        this.models.add(new UserModel(),"userModel");
        this.models.add(new MessageModel(),"messageModel");

        //Declare our list of controller instances and add them to the controllers array
        this.controllers = new CustomArray(Controller.class);
        this.controllers.add(new LoginController(this),"login");
        this.controllers.add(new DashboardController(this), "dashboard");
        this.controllers.add(new ErrorController(this),"error");
        this.controllers.add(new AdminController(this), "admin");

        //set the active controller for this application to the login screen. (default start controller)
        this.setActiveController("login");
        this.updateView(null);
    }

    //**** SET ACTIVE CONTROLLER ****\\
    //sets the active controller based on a string
    public void setActiveController(String key){
        //check if our array key exists for that controller
        if(this.controllers.arrayKeyExists(key)) {
            //if so set the array key
            this.activeController = key;
        }else{
            //else set the controller to the error controller
            this.activeController = "error";
            //create our response array
            CustomArray response = new CustomArray(String.class);
            //set the redirect to the 500 view
            response.add("500","redirect");
            //add the error message and include the controller key that failed
            response.add("Error 500! Failed load controller "+key+"\nredirecting you back to the login","error");
            //finally recall our update view method
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
            response.add("We're sorry the requested sub view "+targetView+"\ncould not be found in controller "+this.activeController,"error");
            //now add a redirect to the response
            response.add("404","redirect");
            //finally we add a target reload for the view and controller
            response.add(targetView,"targetView");
            response.add(this.activeController,"targetController");
            //lastly we set our active controller back to the welcome view
            this.activeController = "error";
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

    //**** GET MODEL ****\\
    public Model getModel(String key){
        //initialize our outcome
        Model outcome = null;

        //check if the model key exists in the array of models
        if(this.models.arrayKeyExists(key)){
            //if so then set the outcome to that model
            outcome = (Model) this.models.getValue(key);
        }else{
            //else we create a response
            CustomArray response = new CustomArray(String.class);
            //add the fatal error message to the response
            response.add("Fatal Error: Unknown model called "+key+"\nPlease contact support","error");
            response.add("welcome","redirect");
            //set the active controller to error
            this.activeController = "login";
            //re update our view
            this.updateView(response);
        }

        //return the outcome
        return outcome;
    }

    //**** MAIN STATIC VOID ****\\
    //tells java to start the program here and creates a new instance of the WhatsAppConsoleEdition main class
    public static void main(String[] args){
        new WhatsAppConsoleEdition();
    }

}
