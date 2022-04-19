// specify the package
package model;

// system imports
import impresario.IView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

// project imports


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class RentalCollection  extends EntityBase
{
    private static final String myTableName = "rental";

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

    private void queryHelper(String query, String message)throws Exception {

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
            System.out.println(message);
            throw new exception.InvalidPrimaryKeyException(message);
        }
    }

    public void findRentalsThatAreCurrentlyCheckedOut() throws Exception {

        //query

        String query = "SELECT * FROM " + myTableName + " WHERE (CheckinDate IS NULL OR CheckinDate = "+ "" + ")";

        queryHelper(query, "There are no Rentals that are currently checked out");
    }

    public void findOverDueRentals() throws Exception { //query might need work

        //query
        Calendar rightNow = Calendar.getInstance();
        Date todaysDate = rightNow.getTime();
        String todaysDateText = new SimpleDateFormat("yyyy-MM-dd").format(todaysDate);

        String query = "SELECT * FROM " + myTableName + " WHERE ((CheckinDate IS NULL) OR (CheckinDate = '')) AND" +
                " DueDate < '" + todaysDateText + "'";

        System.out.println(query);

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

    public Rental retrieve(int rentalId) {
        Rental retValue = null;
        for (int cnt = 0; cnt < rentalList.size(); cnt++) {
            Rental nextRental = (Rental) rentalList.elementAt(cnt);
            String nextRentalId = (String)nextRental.getState("Id");
            if (nextRentalId.equals(""+rentalId) == true) {
                retValue = nextRental;
                break;
            }
        }
        return retValue;
    }

    public int getSize() {
        if(rentalList != null) return rentalList.size();
        return 0;
    }

    public Rental elementAt(int cnt) {
        if (rentalList != null) {
            if ((cnt >= 0) && (cnt < rentalList.size()))
                return (Rental) rentalList.get(cnt);
        }
        return null;
    }

    private void addRental(Rental a) {
        int index = findIndexToAdd(a);
        rentalList.insertElementAt(a, index);
    }

    private int findIndexToAdd(Rental a) {
        int low = 0;
        int high = rentalList.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Rental midSession = (Rental)rentalList.elementAt(middle);

            int result = Rental.compare(a, midSession);

            if (result == 0) {
                return middle;
            }
            else if (result < 0) {
                high = middle - 1;
            }
            else {
                low = middle + 1;
            }
        }
        return low;
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