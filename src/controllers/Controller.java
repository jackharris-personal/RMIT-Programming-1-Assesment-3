//SET PACKAGE
package controllers;

//IMPORT REQUIRED PACKAGES
import views.View;
import core.CustomArray;
import core.WhatsAppConsoleEdition;
import exceptions.InvalidViewException;

//START ABSTRACT CLASS CONTROLLER
public abstract class Controller {

    //create our protected class wide variables that can be accessed by our children controller classes
    //protected view variable to store the view instance
    protected View view;
    //currentView string to store the current subview the controller is targeting
    protected String currentView;
    //protected WhatsAppConsoleEdition instance, this is parsed during the constructor call and is the singleton of the main application.
    protected WhatsAppConsoleEdition whatsAppConsoleEdition;

    //CONSTRUCTOR
    public Controller(WhatsAppConsoleEdition whatsAppConsoleEdition){
        //Accepts the target instance of the singleton main application
        this.whatsAppConsoleEdition = whatsAppConsoleEdition;
    }

    //ABSTRACT METHODS
    //these methods are abstracted and have to be implemented by the children methods
    //protected process input method, accepts a request from a view and processes the input and logic for that request
    protected abstract void processInput(CustomArray request);
    //public updateView method accepts a response and updates the view, will throw a InvalidViewException if an invalid view is provided.
    public abstract void updateView(CustomArray response) throws InvalidViewException;

    //GETTERS
    //get current view getter, will return the currentView to the caller, this is used by the main WhatsAppConsoleEdition on the event
    //a fatal view error or controller error is thrown.
    public String getCurrentView(){
        return this.currentView;
    }

}
