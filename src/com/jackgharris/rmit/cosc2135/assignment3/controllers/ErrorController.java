//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.controllers;

//**** IMPORT PACKAGES ****\\
//Import the packages required by this class
import com.jackgharris.rmit.cosc2135.assignment3.views.ErrorView;
import com.jackgharris.rmit.cosc2135.assignment3.core.CustomArray;
import com.jackgharris.rmit.cosc2135.assignment3.core.WhatsAppConsoleEdition;
import com.jackgharris.rmit.cosc2135.assignment3.exceptions.InvalidViewException;

//**** START CLASS ****\\
//extends and implements the abstract controller class
public class ErrorController extends Controller{

    //Main constructor class, accepts the instance of the main app and parses it to the parent class via the super
    public ErrorController(WhatsAppConsoleEdition whatsAppConsoleEdition) {
        super(whatsAppConsoleEdition);
        //create the view instance for this controller and set it to the protected this.view method.
        this.view = new ErrorView();
    }

    //**** PROCESS INPUT METHOD ****\\
    //this is our main processing method, its job is to take the input provided by the reviews in the main this.request variable
    //and perform any model calls required to for fill the request of that view. We also first extract the user input from the
    //request at the start as well as create our response array ready to be returned back to the view.
    @Override
    protected void processInput(CustomArray request) {

        //First we set the input string to the request input string as provided by the View
        String input = (String) request.getValue("input");

        //Secondly we create our response array that we will be returning back to the view
        CustomArray response = new CustomArray(String.class);

        //process our input for our new 404 page
        if(this.currentView.matches("404")){
            //check if the input matchs 1, if so then we attemp to recall the request that just failed.
            if(input.matches("1")){
                //set the controller to the last targeted controller
                this.whatsAppConsoleEdition.setActiveController((String) request.getValue("targetController"));
                //set the view to the lasted target view
                response.add(request.getValue("targetView"),"redirect");
                //reupdated the main views call
                this.whatsAppConsoleEdition.updateView(response);

            //check if the input matches 2 then just redirect top the login page
            }else if(input.matches("2")){
                //set the active controller to login
                this.whatsAppConsoleEdition.setActiveController("login");
                //set the redirect to the welcome screen
                response.add("welcome","redirect");
                //recall our main update view method
                this.whatsAppConsoleEdition.updateView(response);
            }
        }

    }

    //**** UPDATE VIEW METHOD ****\\
    //This method is called if this controller is active and any controller has called the this.app.updateView method, this triggers
    //a re rendering of the view in the applicable controller, Update View takes a response array of strings as a input but can also
    //receive a null input if no data needs to be send to the front end.
    //Update view method throws a InvalidViewException error on view failure.
    @Override
    public void updateView(CustomArray response) throws InvalidViewException {

        //Step 0:
        //create the main request to build our request data
        CustomArray request = new CustomArray(String.class);
        //Step 1:
        //firstly we check if we have a valid response or if no response was parsed
        if(response == null){
            //if no response has been parsed then we create a dummy array for Strings that is sent to the relevant review, this is
            //important as the views may have variable display options that check if a error key exists before displaying it, parsing
            //null with out initializing this blank array will cause a fatal error.
            response = new CustomArray(String.class);
        }

        //Next we check if a redirect key has been parsed to this controller via the response, this is marked with the key "redirect"
        //if so we set the current view to the value of the redirect.\
        if(response.arrayKeyExists("redirect")){
            this.currentView = (String) response.getValue("redirect");
        }

        //Now we have initialized the response if null and checked for a redirect we can render the current view for this Controller,
        //Views are broken down into subviews with in a view class, for example here our login view has, welcome, login, loginPassword
        //and register subviews. Each view also returns the the input string from the console and sets it to the class wide input variable.

        //render the 404 page if the current view matchs that
        if(this.currentView.matches("404")){

            //get the request from the view
            request = ((ErrorView)this.view).viewNotFound(response);
            //process the request
            this.processInput(request);

        //render the 500 page and get the returned request and parse it to the process input method
        }else if(this.currentView.matches("500")){

            //get the request from the view
            request = ((ErrorView)this.view).controllerNotFound(response);
            //process the request
            this.processInput(request);

        //else render the 403 page and send the request to the process input method
        }else if(this.currentView.matches("403")){

            //get the request from the view
            request = ((ErrorView)this.view).userForbidden(response);
            //process the request
            this.processInput(request);

        }else{
            //else if none of the current views match those options we throw our invalid view exception error
            throw new InvalidViewException();
        }


    }

    //**** END CLASS ****\\
}
