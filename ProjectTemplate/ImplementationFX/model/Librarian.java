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
    private WorkerCollection myWorkers;

    private Worker selectedWorker;
    private Worker modifyWorker;
    private StudentBorrower selectedStudentBorrower;
    private StudentBorrower modifySB;
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
        //createAndShowLoginView();
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
        dependencies.setProperty("Login", "LoginError");
        //dependencies.setProperty("AddBook", "AddBookErrorMessage");
        //dependencies.setProperty("AddBook", "AddBookSuccessMessage");
        dependencies.setProperty("UpdateStatusMessage","");
        dependencies.setProperty("BookData", "TransactionError");
        //dependencies.setProperty("","");

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
        if(key.equals("StudentBorrowerList") == true){
            return myStudentBorrowers;
        }
        else
        if(key.equals("WorkerList") == true){
            return myWorkers;
        }else
        if (selectedStudentBorrower!= null) {
            Object val = selectedStudentBorrower.getState(key);
            if (val != null)
                return val;
            return "";
        }else
        if (selectedWorker!= null) {
            Object val = selectedWorker.getState(key);
            if (val != null)
                return val;
            return "";
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
        else if (key.equals("CancelTransaction") == true)
        {
             createAndShowLibrarianView();
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
        else if (key.equals("DeleteBook") == true)
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

        else if (key.equals("AddStudentBorrower") == true)
        {
            String transType = key;
            transType =transType.trim();
            doTransaction(transType);
        }
        else if (key.equals("AddWorker") == true)
        {
            String transType = key;
            transType =transType.trim();
            doTransaction(transType);
        }
        else if (key.equals("SearchStudentBorrowerView") == true) // begin student borrower delete sequence

        {
            createAndShowSearchStudentBorrowerView();

        }
        else if(key.equals("Delete a Book") ==true){
            createAndShowDeleteABookView();
        }
        else if (key.equals("StudentBorrowerCollectionDeleteView") == true) //Creates new collection
        {

            Properties p = (Properties)value;
            String name = p.getProperty("FirstName");
            myStudentBorrowers = new StudentBorrowerCollection();
            myStudentBorrowers.findStudentBorrowersWithNameLike(name);
            //myStudentBorrowers.display();
            createAndShowStudentBorrowerCollectionDeleteView();

        }
        else if (key.equals("StudentBorrowerCollectionDeleteViewNo") == true) //goes back to old collection
        {
            createAndShowStudentBorrowerCollectionDeleteNoView();
        }
        else if (key.equals("DeleteStudentBorrowerView") == true) // end student borrower delete sequence
        {
            try{
                selectedStudentBorrower = new StudentBorrower((String)value);

            }catch(InvalidPrimaryKeyException e){
                e.printStackTrace();
                transactionErrorMessage="Cannot Find Student Borrower";
            }
            createAndShowDeleteStudentBorrowerView();
        }
        else if (key.equals("SearchStudentBorrowerViewM") == true){ // start Student borrower modify sequence

            createAndShowSearchStudentBorrowerViewM();
        }
        else if (key.equals("StudentBorrowerCollectionModifyView") == true) //creates collection
        {
            Properties p = (Properties)value;
            String fname = p.getProperty("FirstName");
            myStudentBorrowers = new StudentBorrowerCollection();
            myStudentBorrowers.findStudentBorrowersWithNameLike(fname);
            //myStudentBorrowers.display();
            createAndShowStudentBorrowerCollectionModifyView();
        }
        else if (key.equals("ModifyStudentBorrowerView") == true) // end student borrower modify sequence
        {
            try{
                getStudentBorrower((String)value);

            }catch(InvalidPrimaryKeyException e){
                e.printStackTrace();
                transactionErrorMessage="Cannot Find Student Borrower";
            }
            createAndShowModifyStudentBorrowerView();
        }
        else if (key.equals("StudentBorrowerCollectionModifyViewNo") == true) //goes back to old collection
        {
            createAndShowStudentBorrowerCollectionModifyViewNo();
        }
        else if (key.equals("SearchWorkerViewD") == true) //goes back to old collection
        {
            createAndShowSearchWorkerViewD();
        }
        else if (key.equals("WorkerCollectionDeleteView") == true) //goes back to old collection
        {
            Properties p = (Properties)value;
            String fname = p.getProperty("FirstName");
            myWorkers = new WorkerCollection();
            myWorkers.findWorkersWithFirstNameLike(fname);
            //myStudentBorrowers.display();
            createAndShowWorkerCollectionDeleteView();
        }
        else if (key.equals("DeleteWorkerView") == true) //goes to delete screen
        {
            try{
                selectedWorker = new Worker((String)value);
            }catch(InvalidPrimaryKeyException e){
                e.printStackTrace();
                transactionErrorMessage="Cannot Find Worker";
            }
            createAndShowDeleteWorkerView();
        }
        else if (key.equals("WorkerCollectionDeleteViewNo") == true) //goes back to previous collection screen
        {
            createAndShowWorkerCollectionDeleteViewNo();
        }
        else if (key.equals("SearchWorkerViewM") == true) //begin Modify Sequence
        {
            createAndShowSearchWorkerViewM();
        }
        else if (key.equals("WorkerCollectionModifyView") == true) //creates worker collection
        {
            Properties p = (Properties)value;
            String fname = p.getProperty("FirstName");
            myWorkers = new WorkerCollection();
            myWorkers.findWorkersWithFirstNameLike(fname);
            //myStudentBorrowers.display();
            createAndShowWorkerCollectionModifyView();
        }
        else if (key.equals("ModifyWorkerView") == true) //begin Modify Sequence
        {
            try{
                getWorker((String)value);
            }catch(InvalidPrimaryKeyException e){
            e.printStackTrace();
            transactionErrorMessage="Cannot Find Worker";
            }
            createAndShowModifyWorkerView();
        }
        else if (key.equals("WorkerCollectionModifyViewNo") == true) //goes back to old collection
        {
            createAndShowWorkerCollectionModifyViewNo();
        }else if(key.equals("UpdateStudentBorrower")){
            Properties p = (Properties) value;
            modifySB= new StudentBorrower(p);
            modifySB.save("update");
        }else if(key.equals("UpdateWorker")){
            Properties p = (Properties) value;
            modifyWorker= new Worker(p);
            modifyWorker.save("update");
        }else if(key.equals("DeleteStudentBorrower")){
            Properties p = (Properties) value;
            modifySB= new StudentBorrower(p);
            modifySB.save("update");
        }else if(key.equals("DeleteWorker")){
            Properties p = (Properties) value;
            modifyWorker= new Worker(p);
            modifyWorker.save("update");
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
     * Tries to create old persistable student worker object.
     * @param id
     * @throws InvalidPrimaryKeyException
     */

    //-------------------------------------------------------------
    private void getStudentBorrower(String id)throws InvalidPrimaryKeyException {
        try {
            //DEBUG System.out.println("Student Borrower id: "+ id);
            selectedStudentBorrower = new StudentBorrower(id);
            //DEBUG System.out.println(selectedStudentBorrower);
        }
        catch (Exception e){
            transactionErrorMessage = "Cannot find Student Borrower";
            new Event(Event.getLeafLevelClassName(this), "createTransaction", "Trans creation fail", Event.ERROR);
        }
    }
    //-------------------------------------------------------------
    private void getWorker(String id)throws InvalidPrimaryKeyException {
        try {
            System.out.println("Worker id: "+ id);
            selectedWorker = new Worker(id);
            System.out.println(selectedWorker);
        }
        catch (Exception e){
            transactionErrorMessage = "Cannot find Worker";
            new Event(Event.getLeafLevelClassName(this), "createTransaction", "Trans creation fail", Event.ERROR);
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

    //------------------------------------------------------------
    private void createAndShowSearchStudentBorrowerViewM()
    {
        Scene currentScene = (Scene)myViews.get("SearchStudentBorrowerViewM");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchStudentBorrowerViewM", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchStudentBorrowerViewM", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowStudentBorrowerCollectionModifyView()
    {
        Scene currentScene = (Scene)myViews.get("StudentBorrowerCollectionModifyView");


        // create our initial view
        View newView = ViewFactory.createView("StudentBorrowerCollectionModifyView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);
        myViews.put("StudentBorrowerCollectionModifyView", currentScene);


        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowModifyStudentBorrowerView()
    {
        Scene currentScene = (Scene)myViews.get("ModifyStudentBorrowerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("ModifyStudentBorrowerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ModifyStudentBorrowerView", currentScene);
        }

        swapToView(currentScene);

    }

    //------------------------------------------------------------
    private void createAndShowDeleteABookView(){
        Scene currentScene = (Scene)myViews.get("DeleteABookView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("DeleteABookView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("DeleteABookView", currentScene);
        }

        swapToView(currentScene);
    }

    //------------------------------------------------------------
    private void createAndShowStudentBorrowerCollectionModifyViewNo()
    {
        Scene currentScene = (Scene)myViews.get("StudentBorrowerCollectionModifyView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("StudentBorrowerCollectionModifyView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentBorrowerCollectionModifyView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowSearchWorkerViewD()
    {
        Scene currentScene = (Scene)myViews.get("SearchWorkerViewD");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchWorkerViewD", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchWorkerViewD", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowWorkerCollectionDeleteView()
    {
        Scene currentScene = (Scene)myViews.get("WorkerCollectionDeleteView");

        // create our initial view
        View newView = ViewFactory.createView("WorkerCollectionDeleteView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);
        myViews.put("WorkerCollectionDeleteView", currentScene);


        swapToView(currentScene);

    }

    private void createAndShowDeleteWorkerView()
    {
        Scene currentScene = (Scene)myViews.get("DeleteWorkerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("DeleteWorkerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("DeleteWorkerView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowWorkerCollectionDeleteViewNo()
    {
        Scene currentScene = (Scene)myViews.get("WorkerCollectionDeleteView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("WorkerCollectionDeleteView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("WorkerCollectionDeleteView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowSearchWorkerViewM()
    {
        Scene currentScene = (Scene)myViews.get("SearchWorkerViewM");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchWorkerViewM", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchWorkerViewM", currentScene);
        }

        swapToView(currentScene);
    }

    private void createAndShowWorkerCollectionModifyView()
    {
        Scene currentScene = (Scene)myViews.get("WorkerCollectionModifyView");

        // create our initial view
        View newView = ViewFactory.createView("WorkerCollectionModifyView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);
        myViews.put("WorkerCollectionModifyView", currentScene);

        swapToView(currentScene);
    }

    private void createAndShowModifyWorkerView()
    {
        Scene currentScene = (Scene)myViews.get("ModifyWorkerView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ModifyWorkerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ModifyWorkerView", currentScene);
        }

        swapToView(currentScene);
    }

    private void createAndShowWorkerCollectionModifyViewNo()
    {
        Scene currentScene = (Scene)myViews.get("WorkerCollectionModifyView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("WorkerCollectionModifyView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("WorkerCollectionModifyView", currentScene);
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

