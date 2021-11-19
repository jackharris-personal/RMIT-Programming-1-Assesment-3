package com.jackgharris.rmit.cosc2135.models;

import com.jackgharris.rmit.cosc2135.core.CustomArray;

public class Model {

    protected CustomArray errors;

    public Model(){
        this.errors = new CustomArray(String.class);
    }

    //**** GET ERRORS ****\\
    //this method returns an array of errors back to the controller when called
    public CustomArray getErrors(){
        //return this errors
        return this.errors;
    }
}
