//**** SET PACKAGE ****\\
package models;

//**** IMPORT PACKAGES ****\\
import core.CustomArray;

//**** START CLASS ****\\
//this is the parent model class the all the models extend, this contains the core errors array and getErrors method that the controllers
//use to detect when a model has encountered an error.
public class Model {

    //initialize protected errors array
    protected CustomArray errors;

    public Model(){
        //create the array into a new custom array
        this.errors = new CustomArray(String.class);
    }

    //**** GET ERRORS ****\\
    //this method returns an array of errors back to the controller when called
    public CustomArray getErrors(){
        //return this errors
        return this.errors;
    }
}
