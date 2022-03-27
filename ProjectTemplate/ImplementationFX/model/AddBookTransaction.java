// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the DepositTransaction for the ATM application */
//==============================================================
public class AddBookTransaction extends Transaction
{

    private Book myBook;
    private Book oldBook;

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public AddBookTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("AddBook", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        System.out.println("Inside Add Book");
        try
        {
            try {
                oldBook = new Book((String) props.getProperty("barcode"));
            }catch (Exception e){
                myBook = new Book(props);
                myBook.save("add");
                transactionErrorMessage = (String) myBook.getState("UpdateStatusMessage");            }
        } catch (Exception e) {
            transactionErrorMessage = "Error in saving book." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving book " + e.toString(), Event.ERROR);
        }

    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("UpdateStatusMessage") == true)
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
        else
        if ((key.equals("AddBook") == true))
        {
            processTransaction((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("AddBookView");
        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddBookView", this);
            currentScene = new Scene(newView);
            myViews.put("AddBookView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}
