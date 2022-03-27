//specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
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

    private SystemWorker systemUser;

    private StudentBorrowerCollection myStudentBorrowers;
    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage	  	myStage;

    private String transactionErrorMessage = "";
    private String loginErrorMessage= "";

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
        createAndShowLoginView();
        //createAndShowLibrarianView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("BookData", "TransactionError");
        dependencies.setProperty("PatronData", "TransactionError");
        dependencies.setProperty("Search Books", "TransactionError");
        dependencies.setProperty("Search Patrons", "TransactionError");
        dependencies.setProperty("Login", "LoginError");
        dependencies.setProperty("AddBook", "PopulateAddBookMessage");

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
        if (key.equals("LoginError") == true)
        {
            System.out.println(loginErrorMessage);
            return loginErrorMessage;
        }
        else
            return "";
    }


    public void createNewBook(Properties props){
         Book book = new Book(props);
         book.save("add");
         transactionErrorMessage = (String)book.getState("UpdateStatusMessage");

    }
    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("Login") == true)
        {
            if (value != null)
            {
                loginErrorMessage = "";

                boolean flag = loginSystemUser((Properties)value);
                if (flag == true)
                {
                    createAndShowLibrarianView();
                }
            }
        }
        else if(key.equals("LoginView") == true){
            createAndShowLoginView();
        }
        else
        if (key.equals("CancelTransaction") == true)
        {
             createAndShowLibrarianView();
        }


        else if (key.equals("AddStudentBorrowerView") == true)
        {
            createAndShowAddStudentBorrowerView();
        }
        else if (key.equals("AddBook") == true)
        {
            String transType = key;
            transType =transType.trim();
            doTransaction(transType);
        }
        else if (key.equals("ModifyBook") == true)
        {
            String transType = key;
            transType =transType.trim();
            doTransaction(transType);
        }
        else if (key.equals("StudentBorrowerData") == true)
        {
            Properties p = (Properties)value;
            System.out.println(p);
            System.out.println();

        }
        else if (key.equals("SearchStudentBorrowerView") == true)
        {
            createAndShowSearchStudentBorrowerView();

        }
        else if (key.equals("StudentBorrowerCollectionDeleteView") == true) //Creates new collection
        {

            Properties p = (Properties)value;
            String zipCode = p.getProperty("FirstName");
            myStudentBorrowers = new StudentBorrowerCollection();
            myStudentBorrowers.findStudentBorrowersWithFirstNameLike(zipCode);
            createAndShowStudentBorrowerCollectionDeleteView();

        }
        else if (key.equals("StudentBorrowerCollectionDeleteViewNo") == true) //goes back to old collection
        {
            createAndShowStudentBorrowerCollectionDeleteNoView();

        }
        else if (key.equals("DeleteStudentBorrowerView") == true) //*****************************
        {
            createAndShowDeleteStudentBorrowerView();

        }


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
    public boolean loginSystemUser(Properties props)
    {
        try
        {
            systemUser = new SystemWorker(props);
            //DEBUG System.out.println("Error Message: "+loginErrorMessage);
            return true;
        }
        catch (InvalidPrimaryKeyException ex)
        {
            loginErrorMessage = ex.getMessage();
            //DEBUG System.out.println("Error Message: "+loginErrorMessage);
            return false;
        }
        catch (PasswordMismatchException exec)
        {

            loginErrorMessage = exec.getMessage();
            //DEBUG System.out.println("Error Message: "+loginErrorMessage);
            return false;
        }
    }


    /**
     * Create a Transaction depending on the Transaction type (deposit,
     * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
     * create.
     */
    //----------------------------------------------------------
    public void doTransaction(String transactionType){
        try{
            Transaction trans = TransactionFactory.createTransaction(transactionType);

            trans.subscribe("CancelTransaction", this);

            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception e){
            transactionErrorMessage = "ERROR";
            new Event(Event.getLeafLevelClassName(this), "createTransaction", "Trans creation fail", Event.ERROR);
        }
    }

    //------------------------------------------------------------
    private void createAndShowLoginView()
    {
        Scene currentScene = (Scene)myViews.get("LoginView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("LoginView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LoginView", currentScene);
        }

        swapToView(currentScene);

    }
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

    //------------------------------------------------------------
    private void createAndShowAddStudentBorrowerView()
    {
        Scene currentScene = (Scene)myViews.get("AddStudentBorrowerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddStudentBorrowerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("AddStudentBorrowerView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowSearchStudentBorrowerView()
    {
        Scene currentScene = (Scene)myViews.get("SearchStudentBorrowerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchStudentBorrowerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchStudentBorrowerView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowStudentBorrowerCollectionDeleteView()
    {
        Scene currentScene = (Scene)myViews.get("StudentBorrowerCollectionDeleteView");


        // create our initial view
        View newView = ViewFactory.createView("StudentBorrowerCollectionDeleteView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);
        myViews.put("StudentBorrowerCollectionDeleteView", currentScene);


        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowStudentBorrowerCollectionDeleteNoView()
    {
        Scene currentScene = (Scene)myViews.get("StudentBorrowerCollectionDeleteView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("StudentBorrowerCollectionDeleteView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentBorrowerCollectionDeleteView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowDeleteStudentBorrowerView()
    {
        Scene currentScene = (Scene)myViews.get("DeleteStudentBorrowerView");

            // create our initial view
        View newView = ViewFactory.createView("DeleteStudentBorrowerView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);
        myViews.put("DeleteStudentBorrowerView", currentScene);

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

