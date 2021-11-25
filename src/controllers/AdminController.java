//**** SET PACKAGE ****\\
package controllers;

//**** IMPORT REQUIRED PACKAGES ****\\
import models.MessageModel;
import core.CustomArray;
import core.WhatsAppConsoleEdition;
import exceptions.InvalidViewException;
import models.AdminUser;
import views.AdminView;

//**** START CLASS ****\\
//All controller classes extend the main parent controller class and inherit a number of variables and functions from it
public class AdminController extends Controller{

    //Declare class wide variables, in this case we only have one decalred, that is the messageModel
    private final MessageModel messageModel;

    //**** CONSTRUCTOR ****\\
    public AdminController(WhatsAppConsoleEdition whatsAppConsoleEdition) {
        //Parse the main whats app instance up to the parent class via the super() call
        super(whatsAppConsoleEdition);

        //set our messageModel to the singleton instance of the message model via the .getModel() method call
        this.messageModel =(MessageModel) this.whatsAppConsoleEdition.getModel("messageModel");

        //set our view object for the admin controller, in this case its AdminView()
        this.view = new AdminView();
    }

    //**** PROCESS INPUT ****\\
    //this method processes the input for the AdminController class, the input is parsed back to the controller in the form of a
    //custom array from the view object get system input.
    @Override
    protected void processInput(CustomArray request) {

        //First we set the input string to the request input string as provided by the View
        String input = (String) request.getValue("input");

        //Secondly we create our response array that we will be returning back to the view
        CustomArray response = new CustomArray(String.class);

        //**** MENU INPUT PROCESSING ****\\
        //process the main admin menu input, this accepts the input from the user and then redirects the view / controller based on that input
        if(this.currentView.matches("menu")){
            //if the user selects 1 then we set the current view to import and add the isAdmin result to the response before resending the
            //view.
            if(input.matches("1")){
                //set current view
                this.currentView = "import";
                //add the admin status to the view
                response.add(String.valueOf(this.whatsAppConsoleEdition.getCurrentUser().getAdminStatus()),"isAdmin");
                //recall our main update view method and parse it our response
                this.whatsAppConsoleEdition.updateView(response);

            //else if the user selects 2 that indicates they wish to view the session log
            }else if(input.matches("2")){
                //set the current view to the session log view
                this.currentView = "log";
                //add the admin status to the response, this is needed to validate they are an admin when the view loads
                response.add(((AdminUser)this.whatsAppConsoleEdition.getCurrentUser()).getLog(),"log");
                //re call our update view method.
                this.whatsAppConsoleEdition.updateView(response);

            //else if the user selects 3 then we want to go back to the main dashboard
            }else if(input.matches("3")){
                //reset the active controller to the dashboard in the main application class
                this.whatsAppConsoleEdition.setActiveController("dashboard");
                //add the user details tot he response so the view can render accordingly, this includes the admin status and the username
                response.add(this.whatsAppConsoleEdition.getCurrentUser().getUsername(),"username");
                response.add(String.valueOf(this.whatsAppConsoleEdition.getCurrentUser().getAdminStatus()),"isAdmin");
                //add a redirect to tell the controller what view to direct the returning call to
                response.add("home","redirect");
                //recall our update view method and parse it this response
                this.whatsAppConsoleEdition.updateView(response);

                //else it means we have selected a invalid input
            }else{
                //add the error to the response indicating a invalid input was selected
                response.add("invalid option selected","error");
                //recall our update view method and parse the response.
                this.whatsAppConsoleEdition.updateView(response);
            }

            //**** IMPORT MESSAGES PROCESSING ****\\
            //this method processing the import messages function, this accepts a path from the user and imports the messages
            //from the CSV into the program
        }else

            //check if the current view is set to our import view
        if(this.currentView.matches("import") ){

            //delete the past error so as to reset the model ready for a new import
            this.messageModel.getErrors().delete("error");
            //call the load method and parse it our input
            this.messageModel.load(input);

            //create our outcome string
            String outcome;

            //check if we have an error, if not then we add the success result to the response and the outcome
            if (!this.messageModel.getErrors().arrayKeyExists("error")) {
                response.add("csv file imported successfully", "importSuccess");
                outcome = "csv file imported successfully file:"+input;
            } else {
                //else it means we have failed and we should add our error both to the response and the outcome from the model.
                response.add(String.valueOf(this.messageModel.getErrors().getValue("error")), "error");
                outcome = (String.valueOf(this.messageModel.getErrors().getValue("error")));
            }

            //next we check if the user is an admin, if so we log the outcome with the users session log, this stores information
            //about actions they have conducted whilst in this logged in session of the program. session log is not saved.
            if(this.whatsAppConsoleEdition.getCurrentUser().getAdminStatus()){
                ((AdminUser)this.whatsAppConsoleEdition.getCurrentUser()).log("import",outcome);
            }

            //lastly we reset our view back to the menu and recall our update view and parse it our built response
            this.currentView = "menu";
            this.whatsAppConsoleEdition.updateView(response);

        //**** LOG INPUT PROCESSING ****\\
        //process the input for when the curent view matchs the log
        }else if (this.currentView.matches("log")){
            //add the log to the response, this comes from the admin account and is a log for this sessions actions
            response.add(((AdminUser)this.whatsAppConsoleEdition.getCurrentUser()).getLog(),"log");

            //check if the input is 1, if so that indicates they wish to go back to the admin menu
            if(input.matches("1")){
                //reset our current view back to the menu
                this.currentView = "menu";
            }else{
                //else they have not selected the one and only one option so we throw a invalid menu section error back to the view
                response.add("invalid menu selection","error");
            }

            //finally we recall our update view method and parse it the new response.
            this.whatsAppConsoleEdition.updateView(response);
        }

    }


    //**** UPDATE VIEW METHOD ****\\
    //this method accepts a response from any of the controller and will update the view and display it to the console
    //the updateView method will throw a InvalidViewException if an incorrect redirect view has been sent
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
        //Step 2:
        //Next we check if a redirect key has been parsed to this controller via the response, this is marked with the key "redirect"
        //if so we set the current view to the value of the redirect.\
        if(response.arrayKeyExists("redirect")){
            this.currentView = (String) response.getValue("redirect");
        }
        //Step 3:
        //Now we have initialized the response if null and checked for a redirect we can render the current view for this Controller,
        //Views are broken down into subviews with in a view class, for example here our login view has, welcome, login, loginPassword
        //and register subviews. Each view also returns the the input string from the console and sets it to the class wide input variable.

        //MENU VIEW
        if(this.currentView.matches("menu")){
            //get the request from the admin view and parse it the response
            request = ((AdminView)this.view).menu(response);
            //process our input for that request
            this.processInput(request);

        //IMPORT VIEW
        }else if(this.currentView.matches("import")){
            //process the request for the import view and parse it the response
            request = ((AdminView)this.view).load(response);
            //process our input for that request
            this.processInput(request);

        //SESSION LOG VIEW
        }else if(this.currentView.matches("log")){
            //get the request from the log view and parse it the last response to be rendered
            request = ((AdminView)this.view).log(response);
            //reprocess that new request
            this.processInput(request);

            //else if the current view or including redirected failed to match any of those views we throw a InvalidViewException error
        }else{
            throw new InvalidViewException();
        }
    }
}
