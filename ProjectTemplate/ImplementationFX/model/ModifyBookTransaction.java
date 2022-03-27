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
            try {
                oldBook = new Book((String) props.getProperty("barcode"));
                System.out.println("old book"+oldBook);
            }catch (Exception e){
                System.out.println(e);
                myBook = new Book(props);
                System.out.println("new book: "+myBook);
                System.out.println("Selected book: "+selectedBook);
                myBook.save("modify");
                transactionErrorMessage = (String) myBook.getState("UpdateStatusMessage");
            }
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

    private void createAndShowBookView() {
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
            processBarcode((String)value);
        }
        else if(key.equals("ModifyBook")==true){
            processTransaction((Properties)value);

        }

        myRegistry.updateSubscribers(key, this);
    }

    protected void processBarcode(String bc) {
        try {
            selectedBook = new Book(bc);
            System.out.println("Just Selected Book: "+selectedBook);
            selectedBook.display();
            createAndShowBookView();
            System.out.println("Seeing if selected book prints after create and show:"+selectedBook);
        } catch (InvalidPrimaryKeyException e) {
            transactionErrorMessage = "Book Not Found." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in finding book " + e.toString(), Event.ERROR);
        }        }
}