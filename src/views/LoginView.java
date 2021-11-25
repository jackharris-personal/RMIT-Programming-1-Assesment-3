//**** SET PACKAGE ****\\
package views;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import core.CustomArray;

import java.io.IOException;

//**** START CLASS ****\\
public class LoginView extends View{

    //**** WELCOME SCREEN VIEW METHOD ****\\
    //accepts a response from controller in the parameter and displays the content of the response combined with the welcome scree
    public CustomArray welcomeScreen(CustomArray response) {

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //display the title of this view using the helper showTitle method
        this.showTitle("Whats App Console Edition");

        //check if this response has the logout message
        if(response.arrayKeyExists("logout-message")){
            //if so then create an alert and display the logout message with the color of green
            this.showAlert((String) response.getValue("logout-message"), this.textGreen);
        }//else skip this

        //now check if this response has a signup message
        if(response.arrayKeyExists("signup-message")){
            //if so then display te signup message with the alert text color of green
            this.showAlert((String) response.getValue("signup-message"), this.textGreen);
        }//else skip this

        //check if the response has an error, if so display it
        if(response.arrayKeyExists("error")){
            //if we have an error create an alert for the error and set the TextColors to red
            this.showAlert((String) response.getValue("error"), this.textRed);
        }//else skip this


        //Print the main view menu
        System.out.println("1) Login");
        System.out.println("2) Register");
        System.out.println("\nCreated by Jack Harris");

        //get the input from the protected buffered reader and add it to the request under the key of input
        //use a try catch loop to detect any errors
        try {
            //try to set the input key value to the br.readline
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }

        //finally return our request back to the controller
        return request;
    }

    //**** LOGIN SCREEN VIEW METHOD ****\\
    //this view returns the username in the request data that the user wishes to login with
    public CustomArray loginScreenUsername(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the title login
        this.showTitle("Login:");

        //check if we have any errors
        if(response.arrayKeyExists("error")){
            //if so then create an alert and display the errors with the color red
            this.showAlert((String) response.getValue("error"),this.textRed);
        }

        //else we print the main view text, in this case please enter your username
        System.out.println("Please enter your username:");

        //finally we try and get the input from the users keyboard via the protected br.readline
        try {
            //set the input to the input key in a request
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }

        //finally return our request
        return request;
    }

    //**** LOGIN SCREEN PASSWORD VIEW METHOD ****\\
    //this screen allows the user to enter a password and is the second step of the login process
    public CustomArray loginScreenPassword(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the login title using the helper method
        this.showTitle("Login:");

        //add the username as returned and validated by the controller to this request under the same key
        request.add(response.getValue("username"),"username");

        //check if we have any erros to show
        if(response.arrayKeyExists("error")){
            //if so create an alert and display the error
            this.showAlert((String) response.getValue("error"),this.textRed);
        }

        //now we display our main text for this view, in this case its please enter your password
        System.out.println("Please enter your password:");

        //next we get the input from the buffered reader and set it to the request under the input key
        try {
            //set the string to the request under input key
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }

        //finally return the request back to the controller
        return request;
    }

    //**** LOGIN SCREEN PASSWORD VIEW METHOD ****\\
    //this view renders the individual sections of the registration process in sequence based on a current step value that
    //is tracked between the view and the controller
    public CustomArray register(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //initialize the currentStep value to 0, this is overridden if the controller returns a higher step
        int currentStep = 0;

        //show the title register using the showTitle helper function
        this.showTitle("Register:");

        //check if any errors are present
        if(response.arrayKeyExists("error")){
            //if so then create an error for the alert and set the color to red
            this.showAlert((String) response.getValue("error"),this.textRed);
        }

        //check if the controller has returned a registration step
        if(response.arrayKeyExists("registrationStep")){
            //if so set the currentStep to the step returned from the controller response
            currentStep = Integer.parseInt((String) response.getValue("registrationStep"));
        }

        //STEP 0: Display this code if we are on step 0
        if(currentStep == 0){
            //ask the user to enter their chosen username
            System.out.println("Please enter your new username: \nNote: Two users cannot share the same username\n");
            //add the current step we are on to the request
            request.add("0","registrationStep");

        //STEP 1: Display this code if we are on step 0
        }else if(currentStep == 1){
            //ask the user to enter their chosen password
            System.out.println("Please enter your new password:");
            //add the current step we are on to the request
            request.add("1","registrationStep");
            //reparse the validated username to the next request
            request.add(response.getValue("username"),"username");

        //STEP 2: Display this code if we are on step 0
        }else if(currentStep == 2){
            //ask the user to enter their fullname
            System.out.println("Please enter your full name:");
            //add the current step we are on to the request
            request.add("2","registrationStep");
            //reparse the validated username to the next request
            request.add(response.getValue("username"),"username");
            //reparse the validated password to the next request
            request.add(response.getValue("password"),"password");

        //STEP 3: Display this code if we are on step 3
        }else if(currentStep == 3){
            //display the confirmation of new user registration details
            System.out.println("Please confirm your new details below:");
            System.out.println("Username: "+response.getValue("username"));
            System.out.println("Password: "+response.getValue("password"));
            System.out.println("Fullname: "+response.getValue("fullname"));
            //display the options for confirm or cancel
            System.out.println("\nPress 1 to confirm new account or 2 to cancel");
            //set the registration step
            request.add("3","registrationStep");
            //reparse all the response data to the next request
            request.add(response.getValue("username"),"username");
            request.add(response.getValue("password"),"password");
            request.add(response.getValue("fullname"),"fullname");
        }


        try {
            //try and set the value of the bufferedreader system in to the request under the key Input
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }

        //finally return this request to the controller
        return request;
    }

}
