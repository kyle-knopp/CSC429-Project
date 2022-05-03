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
public class AddStudentBorrowerTransaction extends Transaction
{

    private StudentBorrower myStudentBorrower;
    private StudentBorrower oldStudentBorrower;

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public AddStudentBorrowerTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("AddStudentBorrower", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        System.out.println("Inside Add Student Borrower Transaction");
        System.out.println(props.getProperty("FirstName")+props.getProperty("LastName"));
        try
        {
            try {
                oldStudentBorrower = new StudentBorrower((String) props.getProperty("BannerId"));
                transactionErrorMessage="Error: Banner Id already Taken";
            }catch (Exception e){
                myStudentBorrower = new StudentBorrower(props);
                myStudentBorrower.save("add");
                transactionErrorMessage = (String) myStudentBorrower.getState("UpdateStatusMessage");
            }
        } catch (Exception e) {
            transactionErrorMessage = "Error in saving work." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving work " + e.toString(), Event.ERROR);
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
        if ((key.equals("AddStudentBorrower") == true))
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
        Scene currentScene = myViews.get("AddStudentBorrowerView");
        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddStudentBorrowerView", this);
            currentScene = new Scene(newView);
            myViews.put("AddStudentBorrowerView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}
