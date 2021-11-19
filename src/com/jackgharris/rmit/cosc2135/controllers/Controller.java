package com.jackgharris.rmit.cosc2135.controllers;

import com.jackgharris.rmit.cosc2135.core.CustomArray;
import com.jackgharris.rmit.cosc2135.core.WhatsAppConsoleEdition;
import com.jackgharris.rmit.cosc2135.exceptions.InvalidViewException;
import com.jackgharris.rmit.cosc2135.views.View;

public abstract class Controller {

protected View view;
protected String currentView;
protected WhatsAppConsoleEdition whatsAppConsoleEdition;

public abstract void processInput(CustomArray request);
public abstract void updateView(CustomArray response) throws InvalidViewException;

public String getCurrentView(){
    return this.currentView;
}

}
