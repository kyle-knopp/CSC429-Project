package model;

import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class DelinquencyCheckTransaction extends Transaction{
    private Rental myRental;
    private RentalCollection myRentalCollection;
    private StudentBorrower myStudent;

    // GUI Components
    private String transactionErrorMessage = "";

    public DelinquencyCheckTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("Delinquency", "TransactionError");
        dependencies.setProperty("CancelRunDelCheck", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the Book,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        System.out.println("In process Transaction");
        try {
            myRentalCollection = new RentalCollection();
            myRentalCollection.findOverDueRentals();
            int rentalsSize = myRentalCollection.getSize();
            for (int cnt=0; cnt < rentalsSize; cnt++) {
                myRental = myRentalCollection.elementAt(cnt);
                String borrowerId = (String) myRental.getState("BorrowerId");
                myStudent = new StudentBorrower(borrowerId);
                myStudent.setDelinquent();
                myStudent.update();

                //DEBUG System.out.println(myStudent);
            }
            transactionErrorMessage = "Delinquency Check Complete!";
        }
        catch (Exception e) {
            if(e.toString().equals("java.lang.Exception: No rental files found.")) {
                transactionErrorMessage = "No delinquents found!";
            }
            else {
                transactionErrorMessage = "Error: No Delinquencies found";
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in running delinquency check: " + e.toString(),
                        Event.ERROR);
            }
        }
    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        }
        else if (key.equals("UpdateStatusMessage") == true) {
            return transactionErrorMessage;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob") == true) {
            doYourJob();
        }
        else
        if (key.equals("Delinquency") == true) {
            processTransaction((Properties)value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("DelinquencyCheckView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("DelinquencyCheckView", this);
            currentScene = new Scene(newView);
            myViews.put("RunDelinquencyCheckView", currentScene);

            return currentScene;
        }
        else {
            return currentScene;
        }
    }
}
