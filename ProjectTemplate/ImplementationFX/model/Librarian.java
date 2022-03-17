//specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.*;

/** The class containing the Teller  for the ATM application */
//==============================================================
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;


    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage	  	myStage;

    private String transactionErrorMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Librarian()
    {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Librarian");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowLibrarianView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("BookData", "TransactionError");
        dependencies.setProperty("PatronData", "TransactionError");
        dependencies.setProperty("Search Books", "TransactionError");
        dependencies.setProperty("Search Patrons", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
            return "";
    }


    public void createNewBook(Properties props){
         Book book = new Book(props);
         book.save();
        transactionErrorMessage = (String)book.getState("UpdateStatusMessage");

    }
    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);


        if (key.equals("CancelTransaction") == true)
        {
             createAndShowLibrarianView();
        }

        else if (key.equals("Insert New Book") == true)
        {
            createAndShowBookView();
        }
        else if (key.equals("BookData") == true)
        {
            Properties p = (Properties)value;
            createNewBook(p);
        }
        else if (key.equals("Search Books") == true)
        {
            createAndShowSearchBooksView();
        }
        else if (key.equals("Search Patrons") == true)
        {
            createAndShowSearchPatronsView();
        }
        else if(key.equals("Insert Patron") == true){
            createAndShowPatronView();
        }
        else if(key.equals("PatronData")){
            Properties p = (Properties)value;

        }
        else if(key.equals("BookCollection")){
            createAndShowBookCollectionView();
        }
        else if(key.equals("PatronCollection")){
            createAndShowPatronCollectionView();
        }
        /*if ((key.equals("Insert Book") == true) || (key.equals("Insert Patron") == true) ||
                (key.equals("Search Books") == true) || (key.equals("Search Patrons") == true))
        {
            String transType = key;

         //   doTransaction(transType);
        } */

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
   public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /**
     * Login AccountHolder corresponding to user name and password.
     */
    //----------------------------------------------------------


    /**
     * Create a Transaction depending on the Transaction type (deposit,
     * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
     * create.
     */
    //----------------------------------------------------------
   /* public void doTransaction(String transactionType)
    {
        try
        {
            Transaction trans = TransactionFactory.createTransaction(
                    transactionType, myAccountHolder);

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception ex)
        {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }

    //----------------------------------------------------------
    private void createAndShowTransactionChoiceView()
    {
        Scene currentScene = (Scene)myViews.get("TransactionChoiceView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("TransactionChoiceView", currentScene);
        }


        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }*/

    //------------------------------------------------------------
    private void createAndShowLibrarianView()
    {
        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowBookView()
    {
        Scene currentScene = (Scene)myViews.get("BookView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("BookView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("BookView", currentScene);
        }

        swapToView(currentScene);

    }
    private void createAndShowPatronView()
    {
        Scene currentScene = (Scene)myViews.get("PatronView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("PatronView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowSearchBooksView(){
        Scene currentScene = (Scene)myViews.get("SearchBooksView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchBooksView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchBooksView", currentScene);
        }

        swapToView(currentScene);
    }

    private void createAndShowSearchPatronsView(){
        Scene currentScene = (Scene)myViews.get("SearchPatronsView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchPatronsView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchPatronsView", currentScene);
        }

        swapToView(currentScene);
    }

    private void createAndShowBookCollectionView(){
        Scene currentScene = (Scene)myViews.get("BookCollectionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("BookCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("BookCollectionView", currentScene);
        }

        swapToView(currentScene);
    }
    private void createAndShowPatronCollectionView(){
        Scene currentScene = (Scene)myViews.get("PatronCollectionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("PatronCollectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronCollectionView", currentScene);
        }

        swapToView(currentScene);
    }

    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }



    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene)
    {


        if (newScene == null)
        {
            System.out.println("Teller.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }


}

