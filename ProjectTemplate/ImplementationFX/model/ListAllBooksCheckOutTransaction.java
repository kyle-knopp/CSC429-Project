package model;

// system imports
import javafx.scene.Scene;

import java.util.Enumeration;
import java.util.Properties;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class ListAllBooksCheckOutTransaction extends Transaction{

    private BookCollection myBooks;

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public ListAllBooksCheckOutTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();

        dependencies.setProperty("Cancel", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {


    }

    @Override
    protected Scene createView() {
        Scene currentScene = myViews.get(""); // change to whatever view we will create

        if (currentScene == null) {
            View newView = ViewFactory.createView("", this);
            currentScene = new Scene(newView);
            myViews.put("", currentScene);
        }

        return currentScene;
    }

    private void createAndShowView() {  // change to whatever view we will create
        //transactionErrorMessage = "";
        Scene currentScene = (Scene) myViews.get("");
        if (currentScene == null) {
            View newView = ViewFactory.createView("", this);
            currentScene = new Scene(newView);


            myViews.put("", currentScene);
        }

        swapToView(currentScene);
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("UpdateStatusMessage") == true)
        {
            return transactionErrorMessage;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }

        myRegistry.updateSubscribers(key, this);
    }

}

