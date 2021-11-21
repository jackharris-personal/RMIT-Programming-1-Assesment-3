//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.assignment3.views;

//**** IMPORT PACKAGES ****\\
import com.jackgharris.rmit.cosc2135.assignment3.core.TextColors;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//**** START CLASS ****\\
//this is the main view class that all view objects extend, it contains showTitle, showAlert and the bufferedreader references
public class View {

    //create protected instance of buffered reader
    protected BufferedReader br;

    public View(){
        //initialize our buffered reader variable to a new instance of the BufferedReader with a InputSteamReader and system in parsed
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    //**** SHOW TITLE HELPER METHOD ****\\
    //Accepts the view title in string form
    protected void showTitle(String title){
        //display the blue border indicating a new view has loaded
        System.out.println(TextColors.backgroundBlue+"\n"+TextColors.reset);
        //print the title as provided by a sting
        System.out.println(TextColors.textBlueBold+title+"\n"+TextColors.reset);
    }

    //**** SHOW ALERT HELPER METHOD ****\\
    //Accepts a string to display and the textColor of the alert
    protected void showAlert(String alert, String textColor){
        //boarder + textcolor appended
        System.out.println(textColor+"_____________________________________\n");
        //display alert
        System.out.println("Alert: "+alert);
        //end boarder and reset colors appended
        System.out.println("_____________________________________\n"+TextColors.reset);
    }
}
