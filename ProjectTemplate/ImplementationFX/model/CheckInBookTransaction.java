// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the DepositTransaction for the ATM application */
//==============================================================
public class CheckInBookTransaction extends Transaction
{

    private Rental myRental;
    private Rental oldRental;
    private SystemWorker systemWorker;
    private Book myBook;

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public CheckInBookTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CheckInBook", "TransactionError");
        dependencies.setProperty("SubmitBarcode","TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        System.out.println("Process Transaction");
        try {
            myBook = new Book(props.getProperty("barcode"));
            String bookId = myBook.getId();
            Calendar rightNow = Calendar.getInstance();
            Date todayDate = rightNow.getTime();
            String todayDateText = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
            try {
                transactionErrorMessage="Error: Book Not Checked Out.";
                oldRental = new Rental();
                oldRental.findIfBookIsOut(bookId);
            } catch (Exception e) {
                myRental=new Rental(bookId, true);
                myRental.stateChangeRequest("CheckinDate",todayDateText);
                myRental.stateChangeRequest("CheckinWorkerId",systemWorker.getState("bannerID"));
                System.out.println("Rental: "+myRental);
                myRental.update();
                transactionErrorMessage = "Book Successfully Checked In!";
            }
        }catch (Exception e) {
            transactionErrorMessage = "Error in checking in book." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in checking in book " + e.toString(), Event.ERROR);
        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        System.out.println("Get State: "+key);
        if (key.equals("TransactionError") == true)
        {
            System.out.println("Transaction Error: "+transactionErrorMessage);
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
        }else
        if(key.equals("UpdateStatusMessage")==true)
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
            systemWorker=(SystemWorker)value;
            doYourJob();
        }
        else
        if ((key.equals("CheckInBook") == true))
        {
            processTransaction((Properties)value);
        }else
        if ((key.equals("SubmitBarcode") == true))
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
        Scene currentScene = myViews.get("EnterBookBarcodeView");
        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("EnterBookBarcodeView", this);
            currentScene = new Scene(newView);
            myViews.put("EnterBookBarcodeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
}
