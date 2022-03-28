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


/** The class containing the DepositTransaction for the ATM application */
//==============================================================
public class ModifyBookTransaction extends Transaction
{
    private Book myBook;
    private Book oldBook;

    private Book selectedBook;

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public ModifyBookTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("SubmitBarCode", "TransactionError, UpdateStatusMessage, BookToDisplay");
        dependencies.setProperty("ModifyBook", "TransactionError, UpdateStatusMessage");
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
        System.out.println("Inside Modify Book");
        System.out.println(props.getProperty(("barcode")));
        try
        {
            Enumeration keyNames = props.propertyNames();
            while (keyNames.hasMoreElements() == true) {
                String nextKey = (String)keyNames.nextElement();
                String nextVal = props.getProperty(nextKey);
                selectedBook.stateChangeRequest(nextKey, nextVal);
            }
            selectedBook.update();
            transactionErrorMessage = (String) selectedBook.getState("UpdateStatusMessage");
        } catch (Exception e) {
            transactionErrorMessage = "Error in saving book." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in saving book " + e.toString(), Event.ERROR);
        }

    }

    @Override
    protected Scene createView() {
        Scene currentScene = myViews.get("EnterBookBarcodeView");

        if (currentScene == null) {
            View newView = ViewFactory.createView("EnterBookBarcodeView", this);
            currentScene = new Scene(newView);
            myViews.put("EnterBookBarcodeView", currentScene);
        }

        return currentScene;
    }

    private void createAndShowModifyBookView() {

        Scene currentScene = (Scene) myViews.get("ModifyBookView");
        if (currentScene == null) {
            View newView = ViewFactory.createView("ModifyBookView", this);
            currentScene = new Scene(newView);


            myViews.put("ModifyBookView", currentScene);
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
        else if(key.equals("TreeToDisplay")){
            return selectedBook;
        }else {
            if (selectedBook != null) {
                Object val = selectedBook.getState(key);
                if (val != null)
                    return val;
            }
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
        else if ((key.equals("SubmitBarcode") == true))
        {
            //DEBUG System.out.println("Submit Barcode"+(Properties)value);
            processBarcode((Properties) value);
        }
        else if(key.equals("ModifyBook")==true){
            processTransaction((Properties)value);

        }

        myRegistry.updateSubscribers(key, this);
    }

    protected void processBarcode(Properties props) {

        try {
            String bc = props.getProperty("barcode");
            selectedBook = new Book(bc);
            //System.out.println("Seeing if props prints:"+props.getProperty("barcode"));
            //System.out.println("Just Selected Book: "+myBook);
            selectedBook.display();
            createAndShowModifyBookView();

        } catch (InvalidPrimaryKeyException e) {
            transactionErrorMessage = "Book Not Found." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in finding book " + e.toString(), Event.ERROR);
        }        }
}