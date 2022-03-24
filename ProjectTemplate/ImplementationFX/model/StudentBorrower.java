// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.*;
import database.*;

import impresario.*;

import userinterface.*;
import userinterface.*;

/** The class containing the Account for the ATM application */
//==============================================================
public class StudentBorrower extends EntityBase implements IView
{
    private static final String myTableName = "StudentBorrower";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public StudentBorrower(String BannerId)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (BannerId = " + BannerId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one patron  at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one patron. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple Student Borrowers matching id : "
                        + BannerId + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no Student Borrower found for this BannerId, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No Student Borrowers matching id : "
                    + BannerId + " found.");
        }
    }

    //----------------------------------------------------------
    public StudentBorrower(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Update", "UpdateStatusMessage");
        dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("DisplayView") == true)
        {
            createAndShowView();
        }
        else
        if (key.equals("Update") == true)
        {
            updateStateInDatabase();
        }
        else
        {
            persistentState.setProperty(key, (String)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    /** save method */
    //----------------------------------------------------------
    public void save()
    {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase() // should be private? Should this be invoked directly or via the 'sCR(...)' method always?
    {
        try
        {
            if (persistentState.getProperty("BannerId") != null)
            {
                System.out.println("s1");
                Properties whereClause = new Properties();
                whereClause.setProperty("BannerId",
                        persistentState.getProperty("BannerId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Student Borrower data for BannerId : " + persistentState.getProperty("BannerId") + " updated successfully in database!";
            }
            else
            {
                System.out.println("s2");
                Integer BannerId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("BannerId", "" + BannerId.intValue());
                updateStatusMessage = "Student Borrower data for new account : " +  persistentState.getProperty("BannerId")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing account data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {
/*
        View localView = (View)myViews.get("AccountView");

        if (localView == null)
        {
            // create our initial view
            localView = ViewFactory.createView("AccountView", this);

            // NEEDED FOR IMPRESARIO
            localView.subscribe("ServiceCharge", this);
            localView.subscribe("AccountCancelled", this);

            myViews.put("AccountView", localView);

            // make the view visible by installing it into the frame
            swapToView(localView);
        }
        else
        {
            // make the view visible by installing it into the frame
            swapToView(localView);
        } */
    }

    /**
     * This method is needed solely to enable the Account information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector getEntryListView()
    {
        Vector v = new Vector();

        v.addElement(persistentState.getProperty("BannerId"));
        v.addElement(persistentState.getProperty("FirstName"));
        v.addElement(persistentState.getProperty("LastName"));
        v.addElement(persistentState.getProperty("ContactPhone"));
        v.addElement(persistentState.getProperty("Email"));
        v.addElement(persistentState.getProperty("DateOfLatestBorrowerStatus"));
        v.addElement(persistentState.getProperty("DateOfRegistration"));
        v.addElement(persistentState.getProperty("Notes"));
        v.addElement(persistentState.getProperty("status"));

        /*
        BannerId
        FirstName
        LastName
        ContactPhone
        Email
        DateOfLatestBorrowerStatus
        DateOfRegistration
        Notes
        status
         */
        return v;
    }

    public String toString(){

        String s = new String(
                "patronId = " + persistentState.getProperty("BannerId") +
                        "\nname = " + persistentState.getProperty("FirstName") +
                        "\naddress = " + persistentState.getProperty("LastName") +
                        "\ncity = " + persistentState.getProperty("ContactPhone") +
                        "\nstateCode = " + persistentState.getProperty("Email") +
                        "\nzip = " + persistentState.getProperty("DateOfLatestBorrowerStatus") +
                        "\nemail = " + persistentState.getProperty("DateOfRegistration") +
                        "\ndateOfBirth = " + persistentState.getProperty("Notes") +
                        "\nstatus = " + persistentState.getProperty("status"));

        return s;
    }

    public void display(){
        System.out.println(toString());
    }

    //-----------------------------------------------------------------------------------
    public static int compare(StudentBorrower a, StudentBorrower b)
    {
        String aNum = (String)a.getState("BannerId");
        String bNum = (String)b.getState("BannerId");

        return aNum.compareTo(bNum);
    }


    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }


}
