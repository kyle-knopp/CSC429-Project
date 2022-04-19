// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
public class CheckOutBookTransaction extends Transaction
{

    private Rental myRental;
    private Rental oldRental;
    private StudentBorrower myStudent;
    private StudentBorrowerCollection myStudentBorrowers;
    private Book myBook;
    private SystemWorker systemWorker;

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
            myBook = new Book(props.getProperty("BookId"));
            String borrowerId= myStudent.getId();
            props.setProperty("BorrowerId",borrowerId);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String todaysDate=(String)formatter.format(date);
            props.setProperty("CheckoutDate",todaysDate);
            //props.setProperty("CheckoutDate",)
            //System.out.println("My Student:"+myStudent);
            System.out.println("Props: "+props);
            //System.out.println("borrowerId: "+borrowerId);
            try {
                oldRental = new Rental((String) props.getProperty("barcode"));
            }catch (Exception e){
                myRental = new Rental(props);
                myRental.checkOut("checkOut");
                transactionErrorMessage = (String) myRental.getState("UpdateStatusMessage");
            }
        } catch (Exception e) {
            transactionErrorMessage = "Error in checking in book." + e.toString();
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in checking in book " + e.toString(), Event.ERROR);
        }

    }

    public void processSearchStudent(Properties props){
        try{
            String bannerId = props.getProperty("BannerId");
            String name = props.getProperty("FirstName");

            System.out.println("Banner Id: "+bannerId);
            System.out.println("Name: "+name);

            if (bannerId == null || bannerId.length() == 0) {
                myStudentBorrowers = new StudentBorrowerCollection();
                myStudentBorrowers.findStudentBorrowersWithNameLike(name);
                createAndShowStudentCollectionView();

            }
        }catch (Exception e){
            transactionErrorMessage="Error in retrieving Student";
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in retrieving Student",Event.ERROR);
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
        if (key.equals("CheckOutBookErrorMessage") == true)
        {
            return transactionErrorMessage;
        }else
        if(key.equals("CheckOutBookSuccessMessage")==true)
        {
            return transactionErrorMessage;
        }else if (key.equals("StudentBorrowerList")==true){
            return myStudentBorrowers;
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);
        System.out.println("Key: "+key);
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        else
        if ((key.equals("CheckOutBookView") == true))
        {
            try{
                myStudent=new StudentBorrower((String)value);
                createAndShowCheckOutBookView();
            }catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating CheckOutBookView", Event.ERROR);
            }
        }else
        if (key.equals("StudentBorrowerCollectionView")==true){
            processSearchStudent((Properties)value);
        }else if(key.equals("CheckOutBook")==true){
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
        Scene currentScene = myViews.get("SearchSBView");
        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchSBView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchSBView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    private void createAndShowCheckOutBookView() {
        Scene currentScene = (Scene)myViews.get("CheckOutBookView");
        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("CheckOutBookView", this);
            currentScene = new Scene(newView);
            myViews.put("CheckOutBookView", currentScene);
        }
        swapToView(currentScene);
    }


        private void createAndShowStudentCollectionView() {
        Scene currentScene = (Scene)myViews.get("StudentBorrowerCollectionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("StudentBorrowerCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentBorrowerCollectionView", currentScene);
        }
        swapToView(currentScene);
    }
}
