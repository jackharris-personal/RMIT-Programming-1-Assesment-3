//**** SET PACKAGE ****\\
package views;

//**** IMPORT PACKAGES ****\\
import core.CustomArray;
import java.io.IOException;

//**** START CLASS ****\\
//This is the admin view class and it extends the main view parent class, each method contained is a seperate view
public class AdminView extends  View{

    //Menu view, displays the main admin menu, returns a request, accepts a response
    public CustomArray menu(CustomArray response){
        //create our request
        CustomArray request = new CustomArray(String.class);

        //show the view title of welcome and append the usersname of the current user
        this.showTitle("Administrator Menu");

        //check if we have a import success key
        if(response.arrayKeyExists("importSuccess")){
            this.showAlert((String) response.getValue("importSuccess"), this.textGreen);
        }

        //check if we have any errors
        if(response.arrayKeyExists("error")){
            //if so show the errors and set the color to red for the error alert
            this.showAlert((String) response.getValue("error"), this.textRed);
        }

        //display our menu
        System.out.println("1) Import Message CSV");
        System.out.println("2) Session Log");
        System.out.println("3) Back");

        //start our try catch to get input from the user
        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }   //try and get the input from the user via the buffered reader using system.in

        //return our request
        return request;
    }

    //load view, this is the import view for backups of the messages.csv, this returns the request and accepts a response
    public CustomArray load(CustomArray response){

        //create our request
        CustomArray request = new CustomArray(String.class);

        //check if the user is an admin, the key exists then they are
        if(response.arrayKeyExists("isAdmin")) {

            //show the title messages and append the target user of your message
            this.showTitle("Import Messages: ");

            //ask the user to enter the file path of the message they wish to import
            System.out.println("\nPlease enter the file path of the messages.csv you wish to import");

            //check if we have any errors
            if (response.arrayKeyExists("error")) {
                //if so show the errors and set the color to red for the error alert
                this.showAlert((String) response.getValue("error"), this.textRed);
            }
        }else{
            //else we show an authentication error
            this.showAlert("authentication error: import message function requires administration permissions", this.textRed);
        }

        //start our try catch for getting user input
        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }   //try and get the input from the user via the buffered reader using system.in

        //finally return our request
        return request;

    }

    //log view, displays the session log for that admin
    public CustomArray log(CustomArray response){

        //create our request array
        CustomArray request = new CustomArray(String.class);

        //show the session log title using parent protected method
        this.showTitle("Session log");

        //get the log from the response
        String log = (String) response.getValue("log");
        //split the log string into an array
        String[] logArray = log.split(",");

        //check if we have more than 1 value in the log
        if(logArray.length >1) {
            //if so then we use a while loop to iterate over the log and display them
            int i = 0;
            while (i < logArray.length) {
                //display the log entires
                System.out.println(this.textYellow+"_____________________________________________________");
                System.out.println(logArray[i]+this.reset);
                //increase our counter
                i++;
            }
        }else{
            //else if we have no entires we show no log entries found
            showAlert("No session log entries found",this.textYellow);
        }

        //display our menu
        System.out.println("1) back");

        //get user input
        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }   //try and get the input from the user via the buffered reader using system.in


        //return our request
        return request;
    }

}
