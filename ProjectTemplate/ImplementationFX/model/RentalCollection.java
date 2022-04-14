// specify the package
package model;

// system imports
import impresario.IView;

import java.util.Properties;
import java.util.Vector;

// project imports


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class RentalCollection  extends EntityBase
{
    private static final String myTableName = "worker";

    private Vector rentalList;

    private Worker selectedWorker;
    // GUI Components

    // Blank constructor for this class
    //----------------------------------------------------------
    public RentalCollection()
    {
        super(myTableName);

        rentalList = new Vector<Rental>();

    }

    private void queryHelper(String query, String message) {

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextAccountData = (Properties)allDataRetrieved.elementAt(cnt);

                Rental rental = new Rental(nextAccountData);

                if (rental != null)
                {
                    rentalList.addElement(rental);
                }
            }

        }
        else
        {
            //throw new exception.InvalidPrimaryKeyException(message);
            System.out.println(message);
        }
    }

    public void findRentalsThatAreCurrentlyCheckedOut(){

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (CheckinDate IS NULL OR CheckinDate = "+ "" + ")";

        queryHelper(query, "There are no Rentals that are currently checked out");
    }

    public void findOverDueRentals(){ //query might need work

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (CheckinDate IS NULL OR CheckinDate = "+ "" + ")" +
                " AND DueDate < GETDATE()";

        queryHelper(query, "There are no Rentals that are currently checked out");
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Rentals"))
            return rentalList;
        else
        if (key.equals("RentalList"))
            return this;
        /*if (selectedWorker != null) {
            Object val = selectedWorker.getState(key);
            if (val != null)
                return val;
        }*/
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // Class is invariant, so this method does not change any attributes
        // It does handle the request to display its view (if Impose Service Charge -3 is opted for)
        //if (key.equals("DisplayView") == true)
        //{
        //	createAndShowView();
        //}
        //else
        //if (key.equals("AccountSelected") == true
        //{
        //	String accountNumber = (String)value;
        //  Account acct = retrieve(accountNumber);
        //  acct.subscribe("AccountCancelled", this);
        //  acct.stateChangeRequest("DisplayView", this);
        //}
        //else
        //if (key.equals("AccountCancelled") == true)
        //{
        //	createAndShowView();
        //}

        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    /*
    public String toString(){
        String s = ("The Patron Collection contains: ");
        for (Object b : patronList) {
            s.concat(b + " ");
        }
        return s;
    }
     */

    public void display() {
        for (Object b : rentalList) {
            System.out.println(b.toString() + "\n------------------\n");
        }
    }

}