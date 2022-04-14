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
public class CheckOutBookTransaction extends Transaction
{

    private Rental myRental;
    private Rental oldRental;

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public CheckOutBookTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CheckInBook", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        //  DEBUG System.out.println("Inside Add Book");
        try
        {
            try {
                oldRental = new Rental((String) props.getProperty("barcode"));
            }catch (Exception e){
                myRental = new Rental(props);
                myRental.checkIn("checkOut");
                transactionErrorMessage = (String) myRental.getState("UpdateStatusMessage");
            }
        } catch (Exception e) {
            transactionErrorMessage = "Error in checking in book." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in checking in book " + e.toString(), Event.ERROR);
        }

    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        System.out.println(key);
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("CheckInBookErrorMessage") == true)
        {
            return transactionErrorMessage;
        }else
        if(key.equals("CheckInBookSuccessMessage")==true)
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
        if ((key.equals("CheckOutBook") == true))
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
        Scene currentScene = myViews.get("EnterBarcodeView");
        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("EnterBarcodeView", this);
            currentScene = new Scene(newView);
            myViews.put("EnterBarcodeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}
