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
public class DeleteBookTransaction extends Transaction
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
    public DeleteBookTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("SubmitBarcode", "TransactionError");//, UpdateStatusMessage, BookToDisplay");
        dependencies.setProperty("DeleteBook", "TransactionError");//, UpdateStatusMessage");
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
        //System.out.println("Inside Delete Book");
        //System.out.println(props.getProperty(("barcode")));
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
            transactionErrorMessage = "Error in saving book.";
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

    private void createAndShowDeleteBookView() {
        //transactionErrorMessage = "";
        Scene currentScene = (Scene) myViews.get("DeleteBookView");
        if (currentScene == null) {
            View newView = ViewFactory.createView("DeleteBookView", this);
            currentScene = new Scene(newView);


            myViews.put("DeleteBookView", currentScene);
        }

        swapToView(currentScene);
    }
    private void createAndShowConfirmDeleteBookView() {
        //transactionErrorMessage = "";
        Scene currentScene = (Scene) myViews.get("ConfirmDeleteBookView");
        if (currentScene == null) {
            View newView = ViewFactory.createView("ConfirmDeleteBookView", this);
            currentScene = new Scene(newView);


            myViews.put("ConfirmDeleteBookView", currentScene);
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
        else if(key.equals("DeleteBook")==true){
            processTransaction((Properties)value);
        }
        else if(key.equals("BarcodeView")==true){
            createView();
        }
        else if(key.equals("ConfirmDeleteBook")==true){
            createAndShowConfirmDeleteBookView();
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
            transactionErrorMessage = "";
            createAndShowDeleteBookView();

        } catch (InvalidPrimaryKeyException e) {
            transactionErrorMessage = "Error: Book Not Found." ;
            new Event(Event.getLeafLevelClassName(this), "processBarcode",
                    "Error in finding book " + e.toString(), Event.ERROR);
        }
    }
}