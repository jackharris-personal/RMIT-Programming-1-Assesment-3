//**** SET PACKAGE ****\\
package views;

//**** IMPORT PACKAGES ****\\
import java.io.BufferedReader;
import java.io.InputStreamReader;

//**** START CLASS ****\\
//this is the main view class that all view objects extend, it contains showTitle, showAlert and the bufferedreader references
public class View {

    //create protected instance of buffered reader
    protected BufferedReader br;

    //declare the text color to red by appending this to the start of a string
    public final String textRed;
    //set the text color to bold blue by appending this to the start of a strings
    public final String textBlueBold;
    //set the text color to green by appending this to the start of a string
    public final String textGreen;
    //set the text color to yellow by appending this to the start of a string
    public final String textYellow;
    //set the background color to be by appending this to the start of a string
    public final String backgroundBlue;

    //reset string, this is appended to the end of colored text blocks to remove and reset the coloring
    public final String reset = "\033[0m";

    public View(){
        //initialize our buffered reader variable to a new instance of the BufferedReader with a InputSteamReader and system in parsed
        this.br = new BufferedReader(new InputStreamReader(System.in));
        //initialize our text colors and line backgrounds
        this.backgroundBlue = "\033[0;104m";
        this.textYellow = "\u001B[33m";
        this.textGreen = "\033[0;32m";
        this.textBlueBold = "\033[1;97m";
        this.textRed = "\033[0;31m";
    }

    //**** SHOW TITLE HELPER METHOD ****\\
    //Accepts the view title in string form
    protected void showTitle(String title){
        //display the blue border indicating a new view has loaded
        System.out.println(this.backgroundBlue+"\n"+this.reset);
        //print the title as provided by a sting
        System.out.println(this.textBlueBold+title+"\n"+this.reset);
    }

    //**** SHOW ALERT HELPER METHOD ****\\
    //Accepts a string to display and the textColor of the alert
    protected void showAlert(String alert, String textColor){
        //boarder + textcolor appended
        System.out.println(textColor+"_____________________________________\n");
        //display alert
        System.out.println("Alert: "+alert);
        //end boarder and reset colors appended
        System.out.println("_____________________________________\n"+this.reset);
    }
}
